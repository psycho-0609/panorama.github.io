package com.uog.managerarticle.dto;


import javax.validation.constraints.NotNull;


public class ChangePass {

    @NotNull(message = "Please input new password")
    private String newPass;

    @NotNull(message = "Please input new password")
    private String confirmPass;

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
