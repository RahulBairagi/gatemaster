package retrofit;


import javax.inject.Inject;

import app.GateMasterApplication;

import utils.SharedPref;

public class CategoryService {

    @Inject
    SharedPref sharedPref;

    private final GateApi gateApi;

    public CategoryService(GateApi gateApi) {
        this.gateApi = gateApi;
        GateMasterApplication.getComponent().injects(this);
    }

}
