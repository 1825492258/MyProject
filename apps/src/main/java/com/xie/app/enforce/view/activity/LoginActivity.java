package com.xie.app.enforce.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xie.app.enforce.R;
import com.xie.app.enforce.model.impl.LoginModelImpl;
import com.xie.app.enforce.presenter.ILoginPresenter;
import com.xie.app.enforce.presenter.impl.LoginPresenterImpl;
import com.xie.app.enforce.storage.SPManager;
import com.xie.app.enforce.util.common.StatusBarUtil;
import com.xie.app.enforce.util.common.ToastUtils;
import com.xie.app.enforce.util.listener.SampleTextWatch;
import com.xie.app.enforce.util.view.LoadDialog;
import com.xie.app.enforce.util.view.ToolBarHelp;
import com.xie.app.enforce.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录的UI界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    @BindView(R.id.edt_name)
    EditText edtName; // 用户名
    @BindView(R.id.edt_password)
    EditText edtPassWord; // 密码
    @BindView(R.id.img_clean_name)
    ImageView imgNameClean; // 用户名清除
    @BindView(R.id.img_clean_word)
    ImageView imgWordClean; // 密码清除
    @BindView(R.id.btn_login)
    Button btnLogin; // 登录按钮

    private ILoginPresenter mLoginPresenter; // 登录的Presenter
    private LoadDialog mLoadDialog; // Load

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDestroy();
    }

    private void initView() {
        mLoginPresenter = new LoginPresenterImpl(this, new LoginModelImpl());
        // 状态栏的设置
        ToolBarHelp mToolBar = new ToolBarHelp(this);
        mToolBar.setTitle(getString(R.string.login));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolBar.getToolbar());
        String name = SPManager.getInstance().getString("name");
        String password = SPManager.getInstance().getString("password");
        if (!TextUtils.isEmpty(name)) {
            imgNameClean.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(password)) {
            imgWordClean.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
        edtName.setText(name);
        edtPassWord.setText(password);
    }

    private void initListener() {
        edtName.addTextChangedListener(new SampleTextWatch() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    imgNameClean.setVisibility(View.VISIBLE);
                    if (edtPassWord.getText().length() > 0) {
                        btnLogin.setEnabled(true);
                    }
                } else {
                    imgNameClean.setVisibility(View.INVISIBLE);
                    btnLogin.setEnabled(false);
                }
            }
        });
        edtPassWord.addTextChangedListener(new SampleTextWatch() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (editable.length() > 0) {
                    imgWordClean.setVisibility(View.VISIBLE);
                    if (edtName.getText().length() > 0) {
                        btnLogin.setEnabled(true);
                    }
                } else {
                    btnLogin.setEnabled(false);
                    imgWordClean.setVisibility(View.INVISIBLE);
                }
            }
        });
        btnLogin.setOnClickListener(this);
        imgNameClean.setOnClickListener(this);
        imgWordClean.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: // 登录
                mLoginPresenter.requestLogin(edtName.getText().toString(), edtPassWord.getText().toString());
                break;
            case R.id.img_clean_name: // 清除用户名
                edtName.setText(null);
                break;
            case R.id.img_clean_word: // 清除密码
                edtPassWord.setText(null);
                break;
        }
    }

    /**
     * 展示弹窗
     */
    @Override
    public void showLoading() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(this);
        }
        mLoadDialog.show();
    }

    /**
     * 隐藏弹窗
     */
    @Override
    public void hideLoading() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            mLoadDialog.dismiss();
        }
    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 登录失败
     *
     * @param message 信息
     */
    @Override
    public void loginFail(String message) {
        startActivity(new Intent(this, MainActivity.class));
        ToastUtils.showToast(message);
    }
}
