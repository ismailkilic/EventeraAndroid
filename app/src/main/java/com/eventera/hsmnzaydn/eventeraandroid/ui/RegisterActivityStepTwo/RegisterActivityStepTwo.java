package com.eventera.hsmnzaydn.eventeraandroid.ui.RegisterActivityStepTwo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eventera.hsmnzaydn.eventeraandroid.R;
import com.eventera.hsmnzaydn.eventeraandroid.data.DataManager;
import com.eventera.hsmnzaydn.eventeraandroid.data.network.model.Interesting;
import com.eventera.hsmnzaydn.eventeraandroid.data.network.model.RegisterObject;
import com.eventera.hsmnzaydn.eventeraandroid.di.DaggerApplication;
import com.eventera.hsmnzaydn.eventeraandroid.eventbus.RegisterObjectEvent;
import com.eventera.hsmnzaydn.eventeraandroid.ui.Adapters.CategoryListRecyclerViewAdapter;
import com.eventera.hsmnzaydn.eventeraandroid.ui.MainActivity.MainActivity;
import com.eventera.hsmnzaydn.eventeraandroid.ui.base.BaseActivity;
import com.eventera.hsmnzaydn.eventeraandroid.utility.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivityStepTwo extends BaseActivity implements RegisterActivityTwoStepTwoMvpView {

    @BindString(R.string.title_register_interests)
    String title;
    @BindView(R.id.activity_register_step_two_recyclerview)
    RecyclerView activityRegisterStepTwoRecyclerview;

    RegisterActivityStepTwoPresenter presenter;

    @Inject
    DataManager dataManager;

    RegisterObject registerObject;
    CategoryListRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_two);
        ((DaggerApplication) getApplication()).getDaggerComponent().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new RegisterActivityStepTwoPresenter(this, dataManager);
        presenter.getListOfInterests();
        setTitle(title);






    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getRegisterObject(RegisterObjectEvent registerObjectEvent) {
        registerObject = registerObjectEvent.getRegisterObject();

    }


    @OnClick(R.id.activity_register_step_two_register_button)
    public void onClickRegister() {
        registerObject.setInterests(adapter.getSelectedItems());
        presenter.register(registerObject);
    }

    @Override
    public void openMainActivity() {
        Utils.changeActivity(this, MainActivity.class);
    }

    @Override
    public void loadDataToRecylerview(List<Interesting> categoryList) {
        adapter = new CategoryListRecyclerViewAdapter(categoryList);
        activityRegisterStepTwoRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        activityRegisterStepTwoRecyclerview.setAdapter(adapter);
    }
}
