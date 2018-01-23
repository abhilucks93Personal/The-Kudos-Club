package com.viscocits.home_recognize.model;

/**
 * Created by abhi on 11/01/18.
 */

public class ModelResponseKudosPointsData {

    private int RecoRecivedBalanceTime;
    private int InnovationRecivedBalanceTime;
    private String RecoEngagementTotalTimeTEXT;

    public int getRecoRecivedBalanceTime() {
        return RecoRecivedBalanceTime;
    }

    public void setRecoRecivedBalanceTime(int recoRecivedBalanceTime) {
        RecoRecivedBalanceTime = recoRecivedBalanceTime;
    }

    public int getInnovationRecivedBalanceTime() {
        return InnovationRecivedBalanceTime;
    }

    public void setInnovationRecivedBalanceTime(int innovationRecivedBalanceTime) {
        InnovationRecivedBalanceTime = innovationRecivedBalanceTime;
    }

    public String getRecoEngagementTotalTimeTEXT() {
        return RecoEngagementTotalTimeTEXT;
    }

    public void setRecoEngagementTotalTimeTEXT(String recoEngagementTotalTimeTEXT) {
        RecoEngagementTotalTimeTEXT = recoEngagementTotalTimeTEXT;
    }
}
