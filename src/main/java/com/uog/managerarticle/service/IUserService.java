package com.uog.managerarticle.service;

import com.uog.managerarticle.user.CustomUserDetail;

public interface IUserService {
    Object getUserProfile(CustomUserDetail userDetail);
}
