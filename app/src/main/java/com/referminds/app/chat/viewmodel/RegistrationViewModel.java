package com.referminds.app.chat.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.referminds.app.chat.data.Model.SessionManager;
import com.referminds.app.chat.data.Model.User;
import com.referminds.app.chat.R;
import com.referminds.app.chat.view.Activity.Registration;
import com.referminds.app.chat.view.Listners.LoginNavigator;
import com.referminds.app.chat.view.Listners.RegistrationNavigator;
import com.referminds.app.chat.view.Utils.CommonSessionCall;
import com.referminds.app.chat.view.Utils.Utility;


public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator> {

    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> confirm_password = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData;
    public RegistrationViewModel() { }

    public  LiveData<User> getUserValidation() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }

        return userMutableLiveData;
    }


    public void onSignUPbtnClicked() {
        if (password.getValue()!=(null) && confirm_password.getValue()!=null) {
            if (password.getValue().equals(confirm_password.getValue())) {
                User user = new User(username.getValue(),password.getValue());
                userMutableLiveData.setValue(user);
            } else {
                getNavigator().onPasswordMismatched();
            }
        } }
}
