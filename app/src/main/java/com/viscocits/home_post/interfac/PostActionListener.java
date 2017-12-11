package com.viscocits.home_post.interfac;

import com.viscocits.home_post.model.postModels.ModelPostComments;

import java.util.ArrayList;

/**
 * Created by abhi on 06/12/17.
 */

public interface PostActionListener {

    void onPostLikeAction(int pos);

    void onPostSpamAction(int pos);

    void onPostComment(int pos, ArrayList<ModelPostComments> modelPostComments);
}
