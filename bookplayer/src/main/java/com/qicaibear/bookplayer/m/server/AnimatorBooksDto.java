package com.qicaibear.bookplayer.m.server;

import java.util.List;

/**
 * json入口
 */
public class AnimatorBooksDto {
    private PbBooks pbBooks;

    private List<PbBooksDetail> pbBooksDetailList;

    List<AnimatorResponseDto> animatorResponseDtoList;

    List<PbResource> pbResourceList;

    public PbBooks getPbBooks() {
        return pbBooks;
    }

    public void setPbBooks(PbBooks pbBooks) {
        this.pbBooks = pbBooks;
    }

    public List<PbBooksDetail> getPbBooksDetailList() {
        return pbBooksDetailList;
    }

    public void setPbBooksDetailList(List<PbBooksDetail> pbBooksDetailList) {
        this.pbBooksDetailList = pbBooksDetailList;
    }

    public List<AnimatorResponseDto> getAnimatorResponseDtoList() {
        return animatorResponseDtoList;
    }

    public void setAnimatorResponseDtoList(List<AnimatorResponseDto> animatorResponseDtoList) {
        this.animatorResponseDtoList = animatorResponseDtoList;
    }

    public List<PbResource> getPbResourceList() {
        return pbResourceList;
    }

    public void setPbResourceList(List<PbResource> pbResourceList) {
        this.pbResourceList = pbResourceList;
    }
}