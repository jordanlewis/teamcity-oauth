package jetbrains.buildServer.auth.oauth;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jetbrains.buildServer.serverSide.auth.AuthModuleUtil;
import jetbrains.buildServer.serverSide.auth.ServerPrincipal;
import jetbrains.buildServer.users.*;
import jetbrains.buildServer.users.impl.UserImpl;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public class ServerPrincipalFactory {

    private static final Logger LOG = Logger.getLogger(ServerPrincipalFactory.class);

    private static final boolean DEFAULT_ALLOW_CREATING_NEW_USERS_BY_LOGIN = true;
    private static final SimplePropertyKey EMAIL_PROPERTY_KEY = new SimplePropertyKey(UserImpl.VERIFIED_EMAIL_PROPERTY_NAME);

    @NotNull
    private final UserModel userModel;
    private AuthenticationSchemeProperties properties;

    public ServerPrincipalFactory(@NotNull UserModel userModel,
                                  @NotNull AuthenticationSchemeProperties properties) {
        this.userModel = userModel;
        this.properties = properties;
    }

    @NotNull
    public ServerPrincipal getServerPrincipal(@NotNull final String userName, List<String> userEmails,
                                              @NotNull Map<String, String> schemeProperties) {
        ServerPrincipal existingPrincipal;
        LOG.debug("allowMatchingUsersByEMail, userEmails: " + properties.allowMatchingUsersByEmail() + userEmails);
        if (properties.allowMatchingUsersByEmail() && !userEmails.isEmpty()) {
            existingPrincipal = findExistingPrincipalByEmail(userEmails);
        } else {
            existingPrincipal = findExistingPrincipal(userName);
        }
        if (existingPrincipal != null) {
            LOG.info("Use existing user: " + userName);
            return existingPrincipal;
        }
        Map<PropertyKey, String> userProperties = Maps.newHashMap();
        userProperties.put(PluginConstants.ID_USER_PROPERTY_KEY, userName);

        boolean allowCreatingNewUsersByLogin = AuthModuleUtil.allowCreatingNewUsersByLogin(schemeProperties, DEFAULT_ALLOW_CREATING_NEW_USERS_BY_LOGIN);
        if (properties.allowMatchingUsersByEmail() && properties.getNewUserEmailSuffix() != null) {
            for (String userEmail : userEmails) {
                if (userEmail.endsWith(properties.getNewUserEmailSuffix())) {
                    userProperties.put(EMAIL_PROPERTY_KEY, userEmail);
                    break;
                }
            }
        }
        return new ServerPrincipal(PluginConstants.OAUTH_AUTH_SCHEME_NAME, userName, PluginConstants.ID_USER_PROPERTY_KEY, allowCreatingNewUsersByLogin, userProperties);
    }

    private ServerPrincipal findExistingPrincipalByEmail(List<String> userEmails) {
        List<ServerPrincipal> principals = Lists.newArrayList();
        for (String userEmail : userEmails) {
            UserSet<SUser> users = userModel.findUsersByPropertyValue(
                    EMAIL_PROPERTY_KEY, userEmail, false);
            for (SUser user : users.getUsers()) {
                principals.add(new ServerPrincipal(PluginConstants.OAUTH_AUTH_SCHEME_NAME, user.getUsername()));
            }
        }
        if (principals.isEmpty()) {
            return null;
        }
        if (principals.size() > 1) {
            List<String> names = Lists.newArrayListWithCapacity(principals.size());
            for (ServerPrincipal principal : principals) {
                names.add(principal.getName());
            }

            String joinedNames = Joiner.on(", ").join(names);
            LOG.info("Found multiple matches for emails, using the first one: " + joinedNames);
        }
        return principals.get(0);
    }

    @Nullable
    private ServerPrincipal findExistingPrincipal(@NotNull final String userName) {
        try {
            final SUser user = userModel.findUserByUsername(userName, PluginConstants.ID_USER_PROPERTY_KEY);
            if (user != null) {
                return new ServerPrincipal(PluginConstants.OAUTH_AUTH_SCHEME_NAME, user.getUsername());
            }
        } catch (InvalidUsernameException e) {
            // ignore it
        }
        return null;
    }
}
