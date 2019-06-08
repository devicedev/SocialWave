package com.devicedev.socialwave.ui.main.fragments.Profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.devicedev.socialwave.R;
import com.devicedev.socialwave.data.api.clients.UserClient;
import com.devicedev.socialwave.data.room.entities.UserEntity;
import com.devicedev.socialwave.data.utils.ServiceGenerator;
import com.devicedev.socialwave.ui.main.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private String token;

    private ProfileViewModel profileViewModel;


    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageView profileImageView;

    private EditText nameEditText;

    private EditText emailEditText;

    private EditText passwordEditText;

    private TextView birthdayTextView;

    private RadioButton maleRadioButton;

    private RadioButton femaleRadioButton;

    private Button editButton;

    private MainActivity mainActivity;

    public ProfileFragment(String token) {
        this.token = token;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainActivity = (MainActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        initialize(rootView);

        profileViewModel = ViewModelProviders.of(this,
                new ProfileViewModelFactory(
                        token,
                        getActivity().getApplication(),
                        mainActivity
                )
        ).get(ProfileViewModel.class);
        profileViewModel.getUserEntityLiveData().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                if (userEntity == null) {

                    mainActivity.toggleProgressBar(View.VISIBLE);

                    return;

                }
                if (!isVisible()) {

                    mainActivity.toggleProgressBar(View.INVISIBLE);

                }
                if (userEntity.shouldUpdate()) {

                    profileViewModel.update(null);

                }


                updateInputs(userEntity);

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                profileViewModel.update(new ProfileViewModel.OnUpdateUserListener() {
                    @Override
                    public void onUpdateUser() {

                        swipeRefreshLayout.setRefreshing(false);

                    }
                });

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rootView;
    }
    private void updateInputs(@NotNull UserEntity userEntity) {

        nameEditText.setText(userEntity.getName());

        emailEditText.setText(userEntity.getEmail());

        birthdayTextView.setText(userEntity.getBirthday());

        if (userEntity.getGender().equals("male")) {

            maleRadioButton.setChecked(true);

        } else if (userEntity.getGender().equals("female")) {

            femaleRadioButton.setChecked(true);

        }
        GlideUrl glideUrl = new GlideUrl(ServiceGenerator.BASE_URL + "images/" + userEntity.getImage(),
                new LazyHeaders.Builder().addHeader(UserClient.HEADER_KEY, token).build()
        );
        Glide.with(this)
                .load(glideUrl)
                .placeholder(R.drawable.ic_account)
                .into(profileImageView);
    }

    private void initialize(@NonNull View view) {

        nameEditText = view.findViewById(R.id.nameEditText);

        emailEditText = view.findViewById(R.id.emailEditText);

        passwordEditText = view.findViewById(R.id.passwordEditText);

        birthdayTextView = view.findViewById(R.id.birthdayTextView);

        maleRadioButton = view.findViewById(R.id.maleRadioButton);

        femaleRadioButton = view.findViewById(R.id.femaleRadioButton);

        profileImageView = view.findViewById(R.id.profileImageView);

        editButton = view.findViewById(R.id.editButton);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

    }


}
