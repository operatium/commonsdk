package com.qicaibear.bookplayer.m.server;

/**
 * 每一页的json
 */

import java.util.List;

public class AnimatorResponseDto {
    private Integer bookDetailId;

    private List<PbAlphaAnimator> pbAlphaAnimators;
    private List<PbBesselAnimator> pbBesselAnimators;
    private List<PbFrameAnimator> pbFrameAnimators;
    private List<PbPopViewAnimator> pbPopViewAnimators;
    private List<PbRotateAnimator> pbRotateAnimators;
    private List<PbScaleAnimator> pbScaleAnimators;
    private List<PbSeamRollingAnimator> pbSeamRollingAnimators;
    private List<PbSideAnimator> pbSideAnimators;
    private List<PbSoundAnimator> pbSoundAnimators;
    private List<PbTranslationAnimator> pbTranslationAnimators;

    private List<PbActionGroup> groups;

    private List<PbNodeDto> nodes;
    private List<PbNodeGroupR> nodeGroupRList;
    private List<PbNodeRectRDto> nodeRectRDtoList;

    private List<PbRectNodeGroupRR> rectNodeGroupRRList;

    public Integer getBookDetailId() {
        return bookDetailId;
    }

    public void setBookDetailId(Integer bookDetailId) {
        this.bookDetailId = bookDetailId;
    }

    public List<PbAlphaAnimator> getPbAlphaAnimators() {
        return pbAlphaAnimators;
    }

    public void setPbAlphaAnimators(List<PbAlphaAnimator> pbAlphaAnimators) {
        this.pbAlphaAnimators = pbAlphaAnimators;
    }

    public List<PbBesselAnimator> getPbBesselAnimators() {
        return pbBesselAnimators;
    }

    public void setPbBesselAnimators(List<PbBesselAnimator> pbBesselAnimators) {
        this.pbBesselAnimators = pbBesselAnimators;
    }

    public List<PbFrameAnimator> getPbFrameAnimators() {
        return pbFrameAnimators;
    }

    public void setPbFrameAnimators(List<PbFrameAnimator> pbFrameAnimators) {
        this.pbFrameAnimators = pbFrameAnimators;
    }

    public List<PbPopViewAnimator> getPbPopViewAnimators() {
        return pbPopViewAnimators;
    }

    public void setPbPopViewAnimators(List<PbPopViewAnimator> pbPopViewAnimators) {
        this.pbPopViewAnimators = pbPopViewAnimators;
    }

    public List<PbRotateAnimator> getPbRotateAnimators() {
        return pbRotateAnimators;
    }

    public void setPbRotateAnimators(List<PbRotateAnimator> pbRotateAnimators) {
        this.pbRotateAnimators = pbRotateAnimators;
    }

    public List<PbScaleAnimator> getPbScaleAnimators() {
        return pbScaleAnimators;
    }

    public void setPbScaleAnimators(List<PbScaleAnimator> pbScaleAnimators) {
        this.pbScaleAnimators = pbScaleAnimators;
    }

    public List<PbSeamRollingAnimator> getPbSeamRollingAnimators() {
        return pbSeamRollingAnimators;
    }

    public void setPbSeamRollingAnimators(List<PbSeamRollingAnimator> pbSeamRollingAnimators) {
        this.pbSeamRollingAnimators = pbSeamRollingAnimators;
    }

    public List<PbSideAnimator> getPbSideAnimators() {
        return pbSideAnimators;
    }

    public void setPbSideAnimators(List<PbSideAnimator> pbSideAnimators) {
        this.pbSideAnimators = pbSideAnimators;
    }

    public List<PbSoundAnimator> getPbSoundAnimators() {
        return pbSoundAnimators;
    }

    public void setPbSoundAnimators(List<PbSoundAnimator> pbSoundAnimators) {
        this.pbSoundAnimators = pbSoundAnimators;
    }

    public List<PbTranslationAnimator> getPbTranslationAnimators() {
        return pbTranslationAnimators;
    }

    public void setPbTranslationAnimators(List<PbTranslationAnimator> pbTranslationAnimators) {
        this.pbTranslationAnimators = pbTranslationAnimators;
    }

    public List<PbActionGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<PbActionGroup> groups) {
        this.groups = groups;
    }

    public List<PbNodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<PbNodeDto> nodes) {
        this.nodes = nodes;
    }

    public List<PbNodeGroupR> getNodeGroupRList() {
        return nodeGroupRList;
    }

    public void setNodeGroupRList(List<PbNodeGroupR> nodeGroupRList) {
        this.nodeGroupRList = nodeGroupRList;
    }

    public List<PbNodeRectRDto> getNodeRectRDtoList() {
        return nodeRectRDtoList;
    }

    public void setNodeRectRDtoList(List<PbNodeRectRDto> nodeRectRDtoList) {
        this.nodeRectRDtoList = nodeRectRDtoList;
    }

    public List<PbRectNodeGroupRR> getRectNodeGroupRRList() {
        return rectNodeGroupRRList;
    }

    public void setRectNodeGroupRRList(List<PbRectNodeGroupRR> rectNodeGroupRRList) {
        this.rectNodeGroupRRList = rectNodeGroupRRList;
    }
}
