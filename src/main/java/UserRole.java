public class UserRole {
    private int userRoleId;
    private String userRoleName;

    public UserRole() {
    }

    public UserRole(int userRoleId, String userRoleName) {
        this.userRoleId = userRoleId;
        this.userRoleName = userRoleName;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    @Override
    public String toString() {
        return "UserRole{" +
               "userRoleId=" + userRoleId +
               ", userRoleName='" + userRoleName + '\'' +
               '}';
    }
}
