package com.reader.edit.fragment;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yyx.commonsdk.log.MyLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * 通过拍照或者选择相册 裁切压缩后获取一张图片
 * Created by opera on 2018/7/10.
 */

public class TakePhotoFragment extends Fragment {
    private RxPermissions rxPermissions;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PictureConfig.SINGLE:
                        // 图片选择结果回调
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        LocalMedia media = selectList.get(0);
                        String path = media.getPath();
                        if (media.isCut()) {
                            if (media.isCompressed()) {
                                path = media.getCompressPath();
                            } else {
                                path = media.getCutPath();
                            }
                        }
                        takeSuccess(path);
                        break;
                    case PictureConfig.MULTIPLE:
                        // 图片选择结果回调
                        List<LocalMedia> selectL = PictureSelector.obtainMultipleResult(data);
                        ArrayList<String> paths = new ArrayList<>();
                        for (LocalMedia item : selectL){
                            paths.add(item.getPath());
                        }
                        selectImagesSuccess(paths);
                        break;
                }
            }
        }catch (Exception e){
            MyLog.e("201807111103",e.toString(),e);
        }
    }

    //获取到一张图片后的回调
    public void takeSuccess(final String path) {

    }

    //获取到一张图片后的回调
    public void selectImagesSuccess(ArrayList<String> selectList) {

    }

    //拍照
    public void startTakePhoto(FragmentActivity activity,final String picDir){
        rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEachCombined(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Permission>(){
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (permission.granted) {
                            takePhoto(picDir);
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Toast.makeText(getContext(),permission.name+"无权限拍照",Toast.LENGTH_SHORT).show();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        } else {
                            Toast.makeText(getContext(),permission.name+"权限被拒绝，请在设置里面开启相应权限",Toast.LENGTH_SHORT).show();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (disposable != null) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }
                });
    }

    //选择图片
    public void startSelectPic(FragmentActivity activity){
        rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEachCombined(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Permission>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (permission.granted) {
                            selectPic();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Toast.makeText(getContext(),permission.name+"无权限访问相册",Toast.LENGTH_SHORT).show();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        } else {
                            Toast.makeText(getContext(),permission.name+"权限被拒绝，请在设置里面开启相应权限",Toast.LENGTH_SHORT).show();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (disposable != null) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }
                });
    }

    //拍照和选择
    public void startSelectPicAndTake(FragmentActivity activity,final int count){
        rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEachCombined(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Permission>(){
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (permission.granted) {
                            selectPicAndTake(count);
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Toast.makeText(getContext(),permission.name+"无权限拍照",Toast.LENGTH_SHORT).show();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        } else {
                            Toast.makeText(getContext(),permission.name+"权限被拒绝，请在设置里面开启相应权限",Toast.LENGTH_SHORT).show();
                            if (disposable != null) {
                                disposable.dispose();
                                disposable = null;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (disposable != null) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (disposable != null) {
                            disposable.dispose();
                            disposable = null;
                        }
                    }
                });
    }

    public void takePhoto(String picDir){
        try {
            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    .glideOverride(800,800)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .cropWH(800,800)
                    .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    .isDragFrame(true)// 是否可拖动裁剪框(固定)
                    .setOutputCameraPath(picDir)
                    .forResult(PictureConfig.SINGLE);//结果回调onActivityResult code
        } catch (Exception e) {
            MyLog.e("201801291952", e.toString(), e);
        }
    }

    public void selectPic(){
        try {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .imageSpanCount(3)// 每行显示个数 int
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .isCamera(false)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪 true or false
                    .compress(false)// 是否压缩 true or false
                    .glideOverride(800,800)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .cropWH(800,800)
                    .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .minimumCompressSize(10000)// 小于100kb的图片不压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    .isDragFrame(true)// 是否可拖动裁剪框(固定)
                    .forResult(PictureConfig.SINGLE);//结果回调onActivityResult code
        } catch (Exception e) {
            MyLog.e("201807051758", e.toString(), e);
        }
    }

    public void selectPicAndTake(int count){
        try {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .imageSpanCount(3)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    .glideOverride(1200,1200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .cropWH(1200,1200)
                    .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .minimumCompressSize(300)// 小于100kb的图片不压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    .maxSelectNum(count)
                    .isDragFrame(true)// 是否可拖动裁剪框(固定)
                    .forResult(PictureConfig.MULTIPLE);//结果回调onActivityResult code
        } catch (Exception e) {
            MyLog.e("201807051758", e.toString(), e);
        }
    }
}
