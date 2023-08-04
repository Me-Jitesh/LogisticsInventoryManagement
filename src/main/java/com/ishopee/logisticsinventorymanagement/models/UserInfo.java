package com.ishopee.logisticsinventorymanagement.models;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usr_info_tab")
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_info_id_col")
    private Integer id;
    @Column(name = "usr_info_name_col")
    private String name;
    @Column(name = "usr_info_email_col")
    private String email;
    @Column(name = "usr_info_pw_col")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "usr_info_mode_col")
    private UserMode mode = UserMode.DISABLED;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usr_role_map_tab", joinColumns = @JoinColumn(name = "usr_id_fk_col"), inverseJoinColumns = @JoinColumn(name = "role_id_fk_col"))
    private Set<Role> roles;
    @Column(name = "usr_info_otp_col")
    private String OTP;
}
