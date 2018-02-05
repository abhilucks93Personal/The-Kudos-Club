package com.viscocits.home_post.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.viscocits.R;
import com.viscocits.home_post.interfac.PostActionListener;
import com.viscocits.home_post.model.ModelResponseAddComment;
import com.viscocits.home_post.model.ModelResponseCommon;
import com.viscocits.home_post.model.postModels.ModelPostMergedData;
import com.viscocits.home_post.view.ImageListActivity;
import com.viscocits.other.CircleTransform;
import com.viscocits.retrofit.RetrofitApi;
import com.viscocits.utils.Constants;
import com.viscocits.utils.GlideHelper;
import com.viscocits.utils.Utility;
import com.viscocits.utils.zoom.ZoomMultiImageClass;

import java.util.ArrayList;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Activity context;
    private ArrayList<ModelPostMergedData> postDataList;
    private int mLayoutResourceId;
    PostActionListener postActionListener;

    private int selectedPostType = 0, selectedPostCountry = 0;

    public void refreshData(int selectedPostType, int selectedPostCountry) {
        this.selectedPostType = selectedPostType;
        this.selectedPostCountry = selectedPostCountry;
        //   notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDuration, tvContent1, tvContent2, tvLike, tvLikeCount, tvComments, tvSpam, tvMore;
        ImageView ivProfile, ivLogo, ivMainImage1, ivMainImage2, ivMainImage3, ivMainImage4, ivSpam;
        RecyclerView rvComments;
        LinearLayout llLiked, llPostImage2, llPostImagesMain;
        View convertView;
        EditText etComment;

        MyViewHolder(View view) {
            super(view);
            convertView = view;

            ivProfile = (ImageView) view.findViewById(R.id.iv_profile);
            tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            ivLogo = (ImageView) view.findViewById(R.id.iv_type_logo);
            ivMainImage1 = (ImageView) view.findViewById(R.id.post_image1);
            ivMainImage2 = (ImageView) view.findViewById(R.id.post_image2);
            ivMainImage3 = (ImageView) view.findViewById(R.id.post_image3);
            ivMainImage4 = (ImageView) view.findViewById(R.id.post_image4);
            tvMore = (TextView) view.findViewById(R.id.tv_more);
            tvContent1 = (TextView) view.findViewById(R.id.tv_content1);
            tvContent2 = (TextView) view.findViewById(R.id.tv_content2);
            llPostImage2 = (LinearLayout) view.findViewById(R.id.ll_post_image_2);
            llPostImagesMain = (LinearLayout) view.findViewById(R.id.ll_post_images_main);
            tvLike = (TextView) view.findViewById(R.id.tv_like);
            llLiked = (LinearLayout) view.findViewById(R.id.ll_liked);
            tvLikeCount = (TextView) view.findViewById(R.id.tv_like_count);
            tvComments = (TextView) view.findViewById(R.id.tv_comments);
            tvSpam = (TextView) view.findViewById(R.id.tv_spam);
            ivSpam = (ImageView) view.findViewById(R.id.iv_spam);
            rvComments = (RecyclerView) view.findViewById(R.id.rv_comments);
            etComment = (EditText) view.findViewById(R.id.et_comment);
        }
    }


    public PostAdapter(Activity context, PostActionListener postActionListener, int mLayoutResourceId, ArrayList<ModelPostMergedData> postDataList) {
        this.postDataList = postDataList;
        this.postActionListener = postActionListener;
        this.mLayoutResourceId = mLayoutResourceId;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResourceId, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final ModelPostMergedData postData = postDataList.get(position);


       /* Glide.with(context).load(postData.getAvatarExt())
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivProfile);*/

        GlideHelper.loadProfileImageUrl(context, holder.ivProfile, postData.getAvatarExt());

        holder.tvName.setText(postData.getName());

        if (postData.getPostedDate() != null)
            holder.tvDuration.setText(Utility.getFormattedPostDate(postData.getPostedDate()));


        setPostColorAndLogo(holder.tvDuration, holder.ivLogo, postData.getPostType());


        //----------------------------------------------------------------------------- content Post

        if (postData.getPostType().equals("Environment")
                || postData.getPostType().equals("WellBeing")
                || postData.getPostType().equals("Giving")
                || postData.getPostType().equals("Corporate")) {

            holder.tvContent1.setVisibility(View.VISIBLE);

            if (postData.getMessage().toLowerCase().contains("new challenge suggested")) {
                holder.tvContent1.setText(Html.fromHtml("<b>" + postData.getName() + "</b> has suggested a challenge-"));
            } else {
                holder.tvContent1.setText(Html.fromHtml("<b>" + postData.getName() + "</b> has completed a challange-"));
            }
        } else {

            holder.tvContent1.setVisibility(View.GONE);
        }

        holder.tvContent2.setText(Html.fromHtml(postData.getMessage()));


        //-------------------------------------------------------------------------------- like Post

        if (postData.getLIKECOUNT() == 0) {
            holder.tvLike.setVisibility(View.VISIBLE);
            holder.llLiked.setVisibility(View.GONE);
        } else {
            holder.tvLike.setVisibility(View.GONE);
            holder.llLiked.setVisibility(View.VISIBLE);
            holder.tvLikeCount.setText("" + postData.getLIKECOUNT() + "");
        }

        holder.tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeUnlikeApi(position, postData.getPostId(), Constants.paramLike);
            }
        });

        holder.llLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String likeAction = Constants.paramLike;
                if (postData.getLIKEDBYUSER())
                    likeAction = Constants.paramUnLike;

                likeUnlikeApi(position, postData.getPostId(), likeAction);
            }
        });


        //-------------------------------------------------------------------------------- spam Post

        if (postData.getSPAMEDBYUSER()) {
            holder.tvSpam.setVisibility(View.VISIBLE);
            holder.ivSpam.setVisibility(View.GONE);
        } else {
            holder.tvSpam.setVisibility(View.GONE);
            holder.ivSpam.setVisibility(View.VISIBLE);
            holder.ivSpam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSpamPostDialog(position, postData.getPostId()
                            , postData.getClient_Id());
                }
            });
        }

        //------------------------------------------------------------------------------ images Post


        final ArrayList<String> postImages = Utility.getAllPostImages(postData.getPostImagesList()
                , postData.getRecognizeImagesList());

        setPostImages(postImages, holder.llPostImagesMain, holder.ivMainImage1
                , holder.ivMainImage2, holder.llPostImage2, holder.ivMainImage3
                , holder.ivMainImage4, holder.tvMore);

        holder.ivMainImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ImageListActivity.class)
                        .putExtra("pos", 1)
                        .putExtra("urls", postImages));
            }
        });

        holder.ivMainImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ImageListActivity.class)
                        .putExtra("pos", 2)
                        .putExtra("urls", postImages));
            }
        });

        holder.ivMainImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ImageListActivity.class)
                        .putExtra("pos", 3)
                        .putExtra("urls", postImages));
            }
        });

        holder.ivMainImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ImageListActivity.class)
                        .putExtra("pos", 4)
                        .putExtra("urls", postImages));
            }
        });

        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ImageListActivity.class)
                        .putExtra("pos", 5)
                        .putExtra("urls", postImages));
            }
        });


        //---------------------------------------------------------------------------- comments Post

        holder.rvComments.setVisibility(View.GONE);
        if (postData.getCOMMENTCOUNT() == 0) {
            holder.tvComments.setVisibility(View.GONE);
        } else {
            holder.tvComments.setVisibility(View.VISIBLE);
            holder.tvComments.setText("" + postData.getCOMMENTCOUNT() + " Comment(s)");
            holder.tvComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.rvComments.getVisibility() == View.VISIBLE)
                        holder.rvComments.setVisibility(View.GONE);
                    else
                        holder.rvComments.setVisibility(View.VISIBLE);
                }
            });
        }

        CommentsAdapter commentsAdapter = new CommentsAdapter(context
                , R.layout.item_comments
                , postData.getCommentsList());
        holder.rvComments.setAdapter(commentsAdapter);

        holder.etComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    String strComment = holder.etComment.getText().toString().trim();

                    if (strComment.length() > 0) {

                        commentApi(position, postData.getPostId(), strComment);
                        holder.etComment.setText("");
                        Utility.hideKeyboard(context);

                        return true;
                    } else {

                        return true;
                    }
                }

                return false;
            }
        });
    }

    private void setPostColorAndLogo(TextView tvDuration, ImageView ivLogo, String postType) {
        switch (postType) {

            case "Innovation":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeInnovation));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.innovation_logo_post));
                break;

            case "Recognition":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeRecognition));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.recognition_logo_post));
                break;

            case "Environment":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeEnvironment));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.global_logo_post));
                break;

            case "Giving":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeGiving));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.team_logo_post));
                break;

            case "WellBeing":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeWellBeing));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.individual_logo_post));
                break;

            case "Corporate":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeCorporate));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.corporate_logo_post));
                break;

            case "General":
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeGeneral));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.corporate_logo_post));
                break;

            default:
                tvDuration.setBackgroundColor(context.getResources().getColor(R.color.colorPostTypeWork));
                ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.corporate_logo_post));
                break;
        }
    }

    private void commentApi(final int position, int postId, String strComment) {
        RetrofitApi.getInstance().commentPostApi(context, new RetrofitApi.ResponseListener() {
            @Override
            public void _onCompleted() {

            }

            @Override
            public void _onError(Throwable e) {

            }

            @Override
            public void _onNext(Object obj) {
                ModelResponseAddComment response = (ModelResponseAddComment) obj;
                if (response.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                    postActionListener.onPostComment(position, response.getData());
                }
            }
        }, postId, strComment);
    }

    private void showSpamPostDialog(final int position, final int postId
            , final Integer client_id) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_post_spam);
        final EditText etMessage = (EditText) dialog.findViewById(R.id.et_message);

        TextView spamButton = (TextView) dialog.findViewById(R.id.btn_spam);
        spamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = etMessage.getText().toString().trim();
                if (strMessage.length() > 0) {
                    spamPostApi(position, strMessage, postId, client_id);
                    dialog.dismiss();
                }
            }
        });

        TextView cancelButton = (TextView) dialog.findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void spamPostApi(final int position, String message, int postId
            , Integer client_id) {
        RetrofitApi.getInstance().spamPostApi(context, new RetrofitApi.ResponseListener() {
            @Override
            public void _onCompleted() {

            }

            @Override
            public void _onError(Throwable e) {

            }

            @Override
            public void _onNext(Object obj) {
                ModelResponseCommon response = (ModelResponseCommon) obj;
                if (response.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                    postActionListener.onPostSpamAction(position);
                }
            }
        }, postId, message, client_id);
    }

    private void likeUnlikeApi(final int position, int postId, String paramLike) {
        RetrofitApi.getInstance().likePostApi(context, new RetrofitApi.ResponseListener() {
            @Override
            public void _onCompleted() {

            }

            @Override
            public void _onError(Throwable e) {

            }

            @Override
            public void _onNext(Object obj) {
                ModelResponseCommon response = (ModelResponseCommon) obj;

                if (response.getStatusCode().equals(Constants.STATUS_CODE_SUCCESS)) {
                    postActionListener.onPostLikeAction(position);
                }
            }
        }, postId, paramLike);
    }

    private void setPostImages(ArrayList<String> postImages, LinearLayout llPostImagesMain
            , ImageView ivMainImage1, ImageView ivMainImage2, LinearLayout llPostImage2
            , ImageView ivMainImage3, ImageView ivMainImage4, TextView tvMore) {
        ivMainImage1.setVisibility(View.GONE);
        ivMainImage2.setVisibility(View.GONE);
        ivMainImage3.setVisibility(View.GONE);
        ivMainImage4.setVisibility(View.GONE);
        tvMore.setVisibility(View.GONE);
        llPostImage2.setVisibility(View.GONE);


        ViewGroup.LayoutParams params240 = llPostImagesMain.getLayoutParams();
        params240.height = 2400;
        params240.width = ViewGroup.LayoutParams.MATCH_PARENT;

        ViewGroup.LayoutParams params120 = llPostImagesMain.getLayoutParams();
        params120.height = 400;
        params120.width = ViewGroup.LayoutParams.MATCH_PARENT;


        switch (postImages.size()) {

            case 0:
                llPostImagesMain.setVisibility(View.GONE);

                break;

            case 1:
                llPostImagesMain.setVisibility(View.VISIBLE);
                llPostImagesMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800));
                Picasso.with(context)
                        .load(postImages.get(0))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage1);
                ivMainImage1.setVisibility(View.VISIBLE);
                ivMainImage2.setVisibility(View.GONE);
                llPostImage2.setVisibility(View.GONE);
                break;

            case 2:
                llPostImagesMain.setVisibility(View.VISIBLE);
                llPostImagesMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400));
                Picasso.with(context)
                        .load(postImages.get(0))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage1);
                Picasso.with(context)
                        .load(postImages.get(1))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage2);
                ivMainImage1.setVisibility(View.VISIBLE);
                ivMainImage2.setVisibility(View.VISIBLE);
                llPostImage2.setVisibility(View.GONE);
                break;

            case 3:
                llPostImagesMain.setVisibility(View.VISIBLE);
                llPostImagesMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800));
                Picasso.with(context)
                        .load(postImages.get(0))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage1);
                Picasso.with(context)
                        .load(postImages.get(1))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage2);
                Picasso.with(context).load(postImages.get(2)).into(ivMainImage3);
                ivMainImage1.setVisibility(View.VISIBLE);
                ivMainImage2.setVisibility(View.VISIBLE);
                llPostImage2.setVisibility(View.VISIBLE);
                ivMainImage3.setVisibility(View.VISIBLE);
                ivMainImage4.setVisibility(View.VISIBLE);
                tvMore.setVisibility(View.GONE);
                break;

            case 4:
                llPostImagesMain.setVisibility(View.VISIBLE);
                llPostImagesMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800));
                Picasso.with(context)
                        .load(postImages.get(0))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage1);
                Picasso.with(context)
                        .load(postImages.get(1))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage2);
                Picasso.with(context)
                        .load(postImages.get(2))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage3);
                Picasso.with(context)
                        .load(postImages.get(3))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage4);
                ivMainImage1.setVisibility(View.VISIBLE);
                ivMainImage2.setVisibility(View.VISIBLE);
                llPostImage2.setVisibility(View.VISIBLE);
                ivMainImage3.setVisibility(View.VISIBLE);
                ivMainImage4.setVisibility(View.VISIBLE);
                tvMore.setVisibility(View.GONE);
                break;

            default:
                llPostImagesMain.setVisibility(View.VISIBLE);
                llPostImagesMain.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800));
                Picasso.with(context)
                        .load(postImages.get(0))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage1);
                Picasso.with(context)
                        .load(postImages.get(1))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage2);
                Picasso.with(context)
                        .load(postImages.get(2))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage3);
                Picasso.with(context)
                        .load(postImages.get(3))
                        .placeholder(R.color.colorBlack)
                        .error(R.color.colorBlack)
                        .into(ivMainImage4);
                ivMainImage1.setVisibility(View.VISIBLE);
                ivMainImage2.setVisibility(View.VISIBLE);
                llPostImage2.setVisibility(View.VISIBLE);
                ivMainImage3.setVisibility(View.VISIBLE);
                ivMainImage4.setVisibility(View.VISIBLE);
                tvMore.setVisibility(View.VISIBLE);
                tvMore.setText("+" + (postImages.size() - 4));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return postDataList.size();
    }


}