package utopia.shameless.slackbot.model.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.LinkedList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList {
    final Collection<Member> members = new LinkedList<>();

    public Collection<Member> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "members=" + members +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Member{
        String id;
        String team_id;
        String name;
        Boolean is_admin;
        Profile profile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getIs_admin() {
            return is_admin;
        }

        public void setIs_admin(Boolean is_admin) {
            this.is_admin = is_admin;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "id='" + id + '\'' +
                    ", team_id='" + team_id + '\'' +
                    ", name='" + name + '\'' +
                    ", is_admin=" + is_admin +
                    ", profile=" + profile +
                    '}';
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile{
        String display_name;
        String email;

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Profile{" +
                    "display_name='" + display_name + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
