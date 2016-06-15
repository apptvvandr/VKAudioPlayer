package github.y0rrrsh.vkaudioplayer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import github.y0rrrsh.vkaudioplayer.R;
import github.y0rrrsh.vkaudioplayer.fragments.VkItemDialogFragment;
import github.y0rrrsh.vkaudioplayer.utils.SimpleAlertDialog;
import github.y0rrrsh.vkaudioplayer.activities.common.BaseActivity;
import github.y0rrrsh.vkaudioplayer.utils.VKAPPreferences;
import github.y0rrrsh.vkaudioplayer.utils.VKAPUtils;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.switch_auto_sync) SwitchCompat switchAutoSync;
    @BindView(R.id.text_sync_objects) TextView textSyncObjects;
    @BindView(R.id.btn_open_sync_list) ImageButton btnOpenSyncList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        switchAutoSync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            VKAPPreferences.setAutoSyncEnabled(SettingsActivity.this, isChecked);
            updateUi();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    @OnClick({R.id.text_sync_objects, R.id.btn_open_sync_list})
    protected void onOpenSyncListClicked() {
        VkItemDialogFragment.newInstance(null).show(getSupportFragmentManager(), null);
    }

    @OnClick(R.id.btn_clear_account_settings)
    protected void onResetSettingsClicked() {
        SimpleAlertDialog dialog = new SimpleAlertDialog(this,
                R.string.dialog_reset_settings_title, R.string.dialog_reset_settings_message,
                R.string.yes,
                () -> {
                    VKAPUtils.resetSettings(SettingsActivity.this);
                    updateUi();
                },
                R.string.no, null);
        dialog.setCancelable(false);
        dialog.show();
    }

    // TODO: 13.06.16 use SharedPreferencesListener instead
    private void updateUi() {
        boolean autoSyncEnabled = VKAPPreferences.isAutoSyncEnabled(this);
        switchAutoSync.setChecked(autoSyncEnabled);
        int tintColorRes = autoSyncEnabled ? android.R.color.black : R.color.gray;
        int tintColor = getResources().getColor(tintColorRes);
        textSyncObjects.setTextColor(tintColor);
        btnOpenSyncList.setColorFilter(tintColor);
        btnOpenSyncList.setClickable(autoSyncEnabled);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingsActivity.class);
        context.startActivity(starter);
    }
}
