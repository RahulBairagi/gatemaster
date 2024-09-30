package retrofit;


import javax.inject.Inject;

import app.GateMasterApplication;

import utils.SharedPref;

public class CategoryService {

    @Inject
    SharedPref sharedPref;

    private final SencoApi sencoApi;

    public CategoryService(SencoApi sencoApi) {
        this.sencoApi = sencoApi;
        GateMasterApplication.getComponent().injects(this);
    }

}
