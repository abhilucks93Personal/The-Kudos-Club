package com.viscocits.home_recognize;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.viscocits.R;
import com.viscocits.home_recognize.model.ModelResponseKudosPoints;
import com.viscocits.home_recognize.model.ModelResponseKudosPointsData;
import com.viscocits.home_recognize.model.ModelResponsePointsList;
import com.viscocits.home_recognize.model.ModelResponsePointsListData;
import com.viscocits.home_recognize.model.ModelResponseReasonsList;
import com.viscocits.home_recognize.model.ModelResponseReasonsListData;
import com.viscocits.home_recognize.model.ModelResponseRecognitionImage;
import com.viscocits.home_recognize.model.ModelResponseRecognitionSubmit;
import com.viscocits.home_recognize.model.ModelResponseUsersList;
import com.viscocits.home_recognize.model.ModelResponseUsersListData;
import com.viscocits.home_recognize.model.ModelResponseValuesList;
import com.viscocits.home_recognize.model.ModelResponseValuesListData;
import com.viscocits.navigation.MainActivity;
import com.viscocits.retrofit.RetrofitApi;
import com.viscocits.utils.Constants;
import com.viscocits.utils.Utility;
import com.viscocits.utils.crop.CropActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.viscocits.utils.Constants.REQUEST_CAMERA;
import static com.viscocits.utils.Constants.SELECT_FILE;
import static com.viscocits.utils.Constants.cameraPermission;
import static com.viscocits.utils.Constants.cameraRequestCode;
import static com.viscocits.utils.Constants.galleryRequestCode;
import static com.viscocits.utils.Constants.readExternalPermission;
import static com.viscocits.utils.Constants.writeExternalPermission;


/**
 * Created by ng on 2/12/2017.
 */
public class RecognizeFragment extends Fragment implements View.OnClickListener, RetrofitApi.ResponseListener, AdapterView.OnItemClickListener {
    private TextView tvKudosPoints;
    private AutoCompleteTextView actSearch;
    private ProgressBar progressBar;
    private LinearLayout llMain;
    private Spinner spinnerReasons;
    private LinearLayout llValues;
    private EditText etComment;

    private RadioGroup rgPoints;
    private ArrayList<ModelResponseReasonsListData> modelResponseReasonsListData;
    private ArrayList<ModelResponseValuesListData> modelResponseValuesListData;
    private ArrayList<ModelResponsePointsListData> modelResponsePointsListData;
    private ArrayList<CheckBox> valueCheckBoxes = new ArrayList<>();

    ArrayList<ModelResponseUsersListData> modelResponseUsersListData;
    ModelResponseKudosPointsData modelResponseKudosPointsData;
    ModelResponseUsersListData selectedUserData;

    ArrayList<ModelResponseValuesListData> selectedValue;
    String selectedPoint = "";
    private Uri mCapturedImageURI;
    private Uri mImageUri = null;
    private int selected_position = -1;

    boolean isDataLoaded;
    private long recognitionId = -1;
    private Dialog dialog;
    private boolean isDialogClicked;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null && isAdded() && isDataLoaded) {
            endRecognition(false);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recognize, container, false);

        findViewById(view);

        initUi();

        return view;
    }

    private void findViewById(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        llMain = (LinearLayout) view.findViewById(R.id.ll_main);

        actSearch = (AutoCompleteTextView) view.findViewById(R.id.act_search);
        actSearch.setOnItemClickListener(this);
        TextView tvSubmit = (TextView) view.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(this);
        RelativeLayout rl_info = (RelativeLayout) view.findViewById(R.id.rl_info);
        rl_info.setOnClickListener(this);

        spinnerReasons = (Spinner) view.findViewById(R.id.spinner_reasons);
        llValues = (LinearLayout) view.findViewById(R.id.ll_value);
        rgPoints = (RadioGroup) view.findViewById(R.id.rg_points);

        tvKudosPoints = (TextView) view.findViewById(R.id.tv_kudos_points);
        etComment = (EditText) view.findViewById(R.id.et_comment);

    }

    private void initUi() {


        getData();
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);

        getKudosPointsData();

    }

    private void getKudosPointsData() {
        RetrofitApi.getInstance().getKudosPoints(getActivity(), this);
    }

    private void getUsersListData() {
        RetrofitApi.getInstance().getUsersList(getActivity(), this);
    }

    private void getReasonsListData() {
        RetrofitApi.getInstance().getReasonsList(getActivity(), this);
    }

    private void getValuesListData() {
        RetrofitApi.getInstance().getValuesList(getActivity(), this);
    }

    private void getPointsListData() {
        RetrofitApi.getInstance().getPointsList(getActivity(), this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.rl_info:

                showRecognizeInfoDialog(getActivity());

                break;

            case R.id.tv_submit:
                submitRecognition();
                break;
        }
    }

    private void submitRecognition() {
        int userId;
        if (selectedUserData != null)
            userId = (int) selectedUserData.getValue();
        else {
            Utility.showToast(getActivity(), Constants.error_msg_select_user);
            return;
        }

        int reasonId;
        if (modelResponseReasonsListData.get(spinnerReasons.getSelectedItemPosition()) != null)
            reasonId = modelResponseReasonsListData.get(spinnerReasons.getSelectedItemPosition()).getReason_Id();
        else {
            Utility.showToast(getActivity(), Constants.error_msg_select_reason);
            return;
        }

        String rewardId = "";
        if (selectedValue != null && selectedValue.size() > 0) {
            int index = 0;
            for (ModelResponseValuesListData model : selectedValue) {
                rewardId = rewardId + model.getReward_Id();
                if (index != selectedValue.size() - 1)
                    rewardId = rewardId + ",";
                index++;
            }

        } else {
            Utility.showToast(getActivity(), Constants.error_msg_select_value);
            return;
        }


        String valueTitle;
        if (selectedPoint.length() > 0)
            valueTitle = selectedPoint;
        else {
            Utility.showToast(getActivity(), Constants.error_msg_select_points);
            return;
        }

        String supportingText = etComment.getText().toString().trim();

        if (supportingText.length() == 0) {
            Utility.showToast(getActivity(), Constants.error_msg_supporting_text);
            return;
        }


        RetrofitApi.getInstance().submitRecognition(getActivity(),
                this,
                userId, reasonId, rewardId, valueTitle, supportingText);

    }


    public static void showRecognizeInfoDialog(Activity activity) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_recognize_info, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog infoDialog = dialogBuilder.create();
        infoDialog.show();

        TextView tvSubmit = (TextView) dialogView.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });

    }

    @Override
    public void _onCompleted() {
        // progressBar.setVisibility(View.GONE);
    }

    @Override
    public void _onError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void _onNext(Object obj) {
        if (obj instanceof ModelResponseKudosPoints) {
            ModelResponseKudosPoints modelResponseKudosPoints = (ModelResponseKudosPoints) obj;
            if (modelResponseKudosPoints.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseKudosPointsData = modelResponseKudosPoints.getData();
                setKudosPointsData();
                getUsersListData();
            }
        } else if (obj instanceof ModelResponseUsersList) {
            ModelResponseUsersList modelResponseUsersList = (ModelResponseUsersList) obj;
            if (modelResponseUsersList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseUsersListData = modelResponseUsersList.getData();
                setUsersData();
                getReasonsListData();
            }
        } else if (obj instanceof ModelResponseReasonsList) {
            ModelResponseReasonsList modelResponseReasonsList = (ModelResponseReasonsList) obj;
            if (modelResponseReasonsList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseReasonsListData = modelResponseReasonsList.getData();
                setReasonsData();
                getValuesListData();
            }
        } else if (obj instanceof ModelResponseValuesList) {
            ModelResponseValuesList modelResponseValuesList = (ModelResponseValuesList) obj;
            if (modelResponseValuesList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponseValuesListData = modelResponseValuesList.getData();
                setValuesData();
                getPointsListData();
            }
        } else if (obj instanceof ModelResponsePointsList) {
            ModelResponsePointsList modelResponsePointsList = (ModelResponsePointsList) obj;
            if (modelResponsePointsList.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                modelResponsePointsListData = modelResponsePointsList.getData();
                setPointsData();
                progressBar.setVisibility(View.GONE);
                llMain.setVisibility(View.VISIBLE);
                isDataLoaded = true;
            }
        } else if (obj instanceof ModelResponseRecognitionSubmit) {
            ModelResponseRecognitionSubmit modelResponseRecognitionSubmit = (ModelResponseRecognitionSubmit) obj;
            if (modelResponseRecognitionSubmit.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                Utility.addPreferences(getActivity(),Constants.isPostUpdated,true);
                recognitionId = modelResponseRecognitionSubmit.getData();
                showImageDialog();

            }
        } else if (obj instanceof ModelResponseRecognitionImage) {
            ModelResponseRecognitionImage modelResponseRecognitionImage = (ModelResponseRecognitionImage) obj;
            if (modelResponseRecognitionImage.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
            } else {
                Utility.showToast(getActivity(), "Something went wrong!\n" + modelResponseRecognitionImage.getStatusMsg());
            }
            endRecognition(true);
        }
    }

    private void showImageDialog() {


        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialo_image_recognition);

        Window window = dialog.getWindow();
        if (window != null)
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tvCamera = dialog.findViewById(R.id.tv_camera);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchCameraImage();
            }


        });
        TextView tvGallery = dialog.findViewById(R.id.tv_gallery);
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchGalleryImage();
            }


        });
        ImageView ivCross = dialog.findViewById(R.id.iv_cross);
        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endRecognition(true);
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (!isDialogClicked)
                    endRecognition(false);
            }
        });

        dialog.show();


    }

    private void endRecognition(boolean isSwitchTab) {
        mImageUri = null;

        actSearch.setText("");
        selectedUserData = null;

        spinnerReasons.setSelection(0);

        selectedValue.clear();
        selected_position = -1;
        notifyCheckBoxes();

        selectedPoint = modelResponsePointsListData.get(0).getValueTitle();

        etComment.setText("");

        isDialogClicked = false;

        if (isSwitchTab)
            ((MainActivity) getActivity()).switchTab(0);

    }

    private void fetchCameraImage() {

        if (Utility.checkPermissions(getActivity(), readExternalPermission, writeExternalPermission, cameraPermission)) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.TITLE, getActivity().getPackageName());
            mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{readExternalPermission, writeExternalPermission, cameraPermission}, cameraRequestCode);
        }
    }

    private void fetchGalleryImage() {

        if (Utility.checkPermissions(getActivity(), readExternalPermission, writeExternalPermission, cameraPermission)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{readExternalPermission, writeExternalPermission, cameraPermission}, galleryRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 51:
                if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), permissions[1]) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {
                    fetchGalleryImage();
                }
                break;

            case 52:
                if (ActivityCompat.checkSelfPermission(getActivity(), permissions[0]) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), permissions[1]) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {
                    fetchCameraImage();
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {


            case 100:
                if (resultCode == RESULT_OK) {

                    startActivityForResult(new Intent(getActivity(), CropActivity.class)
                            .putExtra("uri", mCapturedImageURI), 500);
                }
                break;


            case 200:

                if (resultCode == RESULT_OK) {
                    Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);
                    startActivityForResult(new Intent(getActivity(), CropActivity.class)
                            .putExtra("uri", imageUri), 500);
                }
                break;


            case 500:
                if (resultCode == RESULT_OK) {

                    mImageUri = data.getParcelableExtra("URI");
                    if (mImageUri != null && recognitionId >= 0)
                        uploadImageRecognition();

                }
                break;
        }

    }

    private void uploadImageRecognition() {

        if (dialog != null && dialog.isShowing()) {
            isDialogClicked = true;
            dialog.dismiss();
        }

        final InputStream imageStream;
        try {
            imageStream = getActivity().getContentResolver().openInputStream(mImageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(selectedImage);
            RetrofitApi.getInstance().uploadImageRecognition(getActivity(), this, encodedImage, recognitionId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    private void setKudosPointsData() {
        int kudosPoints = modelResponseKudosPointsData.getRecoRecivedBalanceTime() + modelResponseKudosPointsData.getInnovationRecivedBalanceTime();
        tvKudosPoints.setText("" + kudosPoints + " Points");
    }

    private void setUsersData() {

        ArrayAdapter<ModelResponseUsersListData> adapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_list_item_1, modelResponseUsersListData);
        actSearch.setAdapter(adapter);
    }


    private void setPointsData() {
        RadioGroup.LayoutParams rprms;
        int index = 0;
        for (final ModelResponsePointsListData listData : modelResponsePointsListData) {

            AppCompatRadioButton radioButton = new AppCompatRadioButton(getActivity());

            if (index == 0) {
                selectedPoint = listData.getValueTitle();
                radioButton.setChecked(true);
            }
            radioButton.setId(index);
            radioButton.setHighlightColor(getResources().getColor(R.color.tab_background_selected));
            radioButton.setText(listData.getValueTitle());
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        selectedPoint = listData.getValueTitle();
                }
            });
            rprms = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.WRAP_CONTENT);
            rprms.weight = 1;
            rgPoints.addView(radioButton, rprms);
            index++;
        }
    }

    private void setValuesData() {
        selectedValue = new ArrayList<>();
        int index = 0;
        for (final ModelResponseValuesListData listData : modelResponseValuesListData) {

            AppCompatCheckBox checkBox = new AppCompatCheckBox(getActivity());
            checkBox.setId(index);
            checkBox.setHighlightColor(getResources().getColor(R.color.tab_background_selected));
            checkBox.setText(listData.getRewardTitle());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        selectedValue.add(listData);
                    } else {
                        selectedValue.remove(listData);
                    }


                }
            });
            valueCheckBoxes.add(checkBox);
            llValues.addView(checkBox);
            index++;
        }

    }

    private void notifyCheckBoxes() {


        for (CheckBox checkBox : valueCheckBoxes) {

            checkBox.setChecked(false);

        }
    }

    private void setReasonsData() {
        ArrayAdapter<ModelResponseReasonsListData> reasonAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, modelResponseReasonsListData);
        spinnerReasons.setAdapter(reasonAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getItemAtPosition(position);
        if (item instanceof ModelResponseUsersListData) {
            selectedUserData = (ModelResponseUsersListData) item;
        }
    }
}
