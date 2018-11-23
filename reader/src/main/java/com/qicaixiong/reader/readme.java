package com.qicaixiong.reader;
public class readme { }
/**
                                            此工程说明文件
 1. xml下标 <= 4
 3. 需要提供设备的信息
 5. bookfragment 管理书籍的每一页
 6. pagefragment 管理每一页的事件
 7. 阅读模式：todo:现阶段只能是从外部传入模式，中间不可以切换模式
    1.默认
    2.自动阅读 每一页的语音播放完毕后自动翻页


                                            模块功能
 1.根据bookID 获取书籍资源
 2.下载书籍资源内的图片和语音
 3.解析书籍资源进行播放

                                            模块结构
 1.resource 负责书籍资源的获取，下载，转换，提供
 2.book 负责管理整本书的翻页
 3.page 负责管理每一页
 4.get 负责对外开放的接口

 resource:
 1.CoordinatesConversion 负责 “网络参数” 向 “本地参数” 的转换
 2.m.in 负责 “网络参数”的基础模型类
 3.m.out 负责 “本地参数” 的基础模型类
 4.FileController 负责管理本地书籍资源的目录
 5.Downloader 负责管理本地资源的下载

 book:
 1.viewpager 直接进行管理当前页码
 2.bookFragment 只是一个容器
 3.bookFragment.play 可以启动阅读

 page:
 1.背景是一张大图片
 2.点击区域 => 弹文字 + 播音 :: 是在大图片控件上找矩形坐标区域 （BackgroundControl）
 3.点文字 => 文本常亮 + 播音 :: 是在文本控件上实现 （TextControl ）
 4.点击重读按钮 => 字幕滚动常亮 + 播整段音 :: 是一个valueAnimator + 时间轴int... 实现字幕滚动常亮，播音单独实现（TextTurnControl）
 5.PlaySoundControl 管理所有的含有播放声音的点击事件处理

 */
