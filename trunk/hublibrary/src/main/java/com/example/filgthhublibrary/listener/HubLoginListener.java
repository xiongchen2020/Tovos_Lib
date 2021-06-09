package com.example.filgthhublibrary.listener;

import com.example.filgthhublibrary.network.bean.ResTeamMember;
import com.example.filgthhublibrary.network.bean.ResUserInfo;

import java.util.List;

public interface HubLoginListener {

    void loginSuccess();

    void loginFail();

    void setAllUser(List<ResUserInfo> resUserInfoList);

    void setAllUserThrowable(Throwable throwable);

    void getTeamMember(List<ResTeamMember> resTeamMemberList);

    void getTeamMembersThrowable(Throwable throwable);
}
