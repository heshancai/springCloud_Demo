<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <base href="/"/>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>商品详细页面</title>
    <link rel="stylesheet" href="css/base.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_common.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_header.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_list.css" type="text/css" />
    <link rel="stylesheet" href="css/shop_goods.css" type="text/css" />
    <script type="text/javascript" src="js/jquery.js" ></script>
    <script type="text/javascript" src="js/topNav.js" ></script>
    <script type="text/javascript" src="js/shop_goods.js" ></script>

    <!-- 添加jquery弹窗的依赖 -->
    <script src="widget/dialog/jquery-ui-1.9.2.custom.min.js"></script>
    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">

</head>
<body>
<!-- Header  -wll-2013/03/24 -->
<div class="shop_hd">
    <!-- Header TopNav -->
    <div class="shop_hd_topNav">
        <div class="shop_hd_topNav_all">
            <!-- Header TopNav Left -->
            <div class="shop_hd_topNav_all_left">
                <p>您好，欢迎来到<b><a href="/">疯狂汽车城</a></b>[<a >登录</a>][<a >注册</a>]</p>
            </div>
            <!-- Header TopNav Left End -->

            <!-- Header TopNav Right -->
            <div class="shop_hd_topNav_all_right">
                <ul class="topNav_quick_menu">

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">我的商城<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;" >
                                <ul>
                                    <li><a title="已买到的商品" target="_top" href="#">已买到的商品</a></li>
                                    <li><a title="个人主页" target="_top" href="#">个人主页</a></li>
                                    <li><a title="我的好友" target="_top" href="#">我的好友</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">卖家中心<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <ul>
                                    <li><a title="已售出的商品" target="_top" href="#">已售出的商品</a></li>
                                    <li><a title="销售中的商品" target="_top" href="#">销售中的商品</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">购物车<b>0</b>种商品<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <!--
                                <ul>
                                  <li><a title="已售出的商品" target="_top" href="#">已售出的商品</a></li>
                                  <li><a title="销售中的商品" target="_top" href="#">销售中的商品</a></li>
                                </ul>
                                -->
                                <p>还没有商品，赶快去挑选！</p>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#" class="topNavHover">我的收藏<i></i></a>
                            <div class="topNav_menu_bd" style="display:none;">
                                <ul>
                                    <li><a title="收藏的商品" target="_top" href="#">收藏的商品</a></li>
                                    <li><a title="收藏的店铺" target="_top" href="#">收藏的店铺</a></li>
                                </ul>
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class="topNav_menu">
                            <a href="#">站内消息</a>
                        </div>
                    </li>

                </ul>
            </div>
            <!-- Header TopNav Right End -->
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
    <!-- Header TopNav End -->

    <!-- TopHeader Center -->
    <div class="shop_hd_header">
        <div class="shop_hd_header_logo"><h1 class="logo"><a href="/"><img src="images/logo.png" alt="ShopCZ" /></a><span>ShopCZ</span></h1></div>
        <div class="shop_hd_header_search">
            <ul class="shop_hd_header_search_tab">
                <li id="search" class="current">商品</li>
                <li id="shop_search">店铺</li>
            </ul>
            <div class="clear"></div>
            <div class="search_form">
                <form method="post" action="index.php">
                    <div class="search_formstyle">
                        <input type="text" class="search_form_text" name="search_content" value="搜索其实很简单！" />
                        <input type="submit" class="search_form_sub" name="secrch_submit" value="" title="搜索" />
                    </div>
                </form>
            </div>
            <div class="clear"></div>
            <div class="search_tag">
                <a >宝马</a>
                <a >保时捷</a>
                <a >法拉利</a>
                <a >丰田</a>
                <a >宝骏</a>
            </div>

        </div>
    </div>
    <div class="clear"></div>
    <!-- TopHeader Center End -->

    <!-- Header Menu -->
    <div class="shop_hd_menu">
        <!-- 所有商品菜单 -->

        <div id="shop_hd_menu_all_category" class="shop_hd_menu_all_category"><!-- 首页去掉 id="shop_hd_menu_all_category" 加上clsss shop_hd_menu_hover -->
            <div class="shop_hd_menu_all_category_title"><h2 title="所有商品分类"><a href="javascript:void(0);">所有商品分类</a></h2><i></i></div>
            <div id="shop_hd_menu_all_category_hd" class="shop_hd_menu_all_category_hd">
                <ul class="shop_hd_menu_all_category_hd_menu clearfix">
                    <!-- 单个菜单项 -->
                    <li id="cat_1" class="">
                        <h3><a  title="红旗">红旗</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="女装" >女装</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="男装" >男装</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <!-- 单个菜单项 End -->
                    <li id="cat_2" class="">
                        <h3><a  title="比亚迪">比亚迪</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="鞋子" >鞋子</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="包包" >包包</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_3" class="">
                        <h3><a  title="宝骏">宝骏</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_4" class="">
                        <h3><a  title="丰田">丰田</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_5" class="">
                        <h3><a  title="宝马">宝马</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>

                    <li id="cat_6" class="">
                        <h3><a  title="大众">大众</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li id="cat_7" class="">
                        <h3><a  title="玛莎拉蒂">玛莎拉蒂</a></h3>
                        <div id="cat_1_menu" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li id="cat_8" class="">
                        <h3><a  title="奥迪">奥迪</a></h3>
                        <div id="" class="cat_menu clearfix" style="">
                            <dl class="clearfix">
                                <dt><a href="美容" >美容</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>

                            <dl class="clearfix">
                                <dt><a href="美妆" >美妆</a></dt>
                                <dd>
                                    <a >风衣</a>
                                    <a >长袖连衣裙</a>
                                    <a >毛呢连衣裙</a>
                                    <a >半身裙</a>
                                    <a >小脚裤</a>
                                    <a >加绒打底裤</a>
                                    <a >牛仔裤</a>
                                    <a >打底衫</a>
                                    <a >情侣装</a>
                                    <a >棉衣</a>
                                    <a >毛呢大衣</a>
                                    <a >毛呢短裤</a>
                                </dd>
                            </dl>
                        </div>
                    </li>
                    <li class="more"><a >查看更多分类</a></li>
                </ul>
            </div>
        </div>
        <!-- 所有商品菜单 END -->

        <!-- 普通导航菜单 -->
        <ul class="shop_hd_menu_nav">
            <li class="current_link"><a href="http://localhost"><span>首页</span></a></li>
            <li class="link"><a ><span>团购</span></a></li>
            <li class="link"><a ><span>汽车品牌</span></a></li>
            <li class="link"><a ><span>优惠卷中心</span></a></li>
            <li class="link"><a ><span>积分中心</span></a></li>
            <li class="link"><a ><span>运动车型专场</span></a></li>
            <li class="link"><a ><span>汽车微商城</span></a></li>
        </ul>
        <!-- 普通导航菜单 End -->
    </div>
    <!-- Header Menu End -->

</div>
<div class="clear"></div>
<!-- 面包屑 注意首页没有 -->
<div class="shop_hd_breadcrumb">
    <strong>当前位置：</strong>
    <span>
			<a >首页</a>&nbsp;›&nbsp;
			<a >汽车商品分类</a>&nbsp;›&nbsp;
			<a >限时抢购商品</a>&nbsp;›&nbsp;
		</span>
</div>
<div class="clear"></div>
<!-- 面包屑 End -->

<!-- Header End -->

<!-- Goods Body -->
<div class="shop_goods_bd clear">

    <!-- 商品展示 -->
    <div class="shop_goods_show">
        <div class="shop_goods_show_left">
            <!-- TODO 京东商品展示 -->
            <link rel="stylesheet" href="css/shop_goodPic.css" type="text/css" />
            <script type="text/javascript" src="js/shop_goodPic_base.js"></script>
            <script type="text/javascript" src="js/lib.js"></script>
            <script type="text/javascript" src="js/163css.js"></script>
            <div id="preview">
                <div class=jqzoom id="spec-n1" onClick="window.open('/')">
                    <IMG height="350" src="/back/goods/showimg?imgPath=${goods.fmUrl}" jqimg="/back/goods/showimg?imgPath=${goods.fmUrl}" width="350">
                </div>
                <div id="spec-n5">
                    <div class=control id="spec-left">
                        <img src="images/left.gif" />
                    </div>
                    <div id="spec-list">
                        <ul class="list-h">
                            <#list goods.otherUrls as url>
                                <li><img src="/back/goods/showimg?imgPath=${url}"></li>
                            </#list>
                        </ul>
                    </div>
                    <div class=control id="spec-right">
                        <img src="images/right.gif" />
                    </div>

                </div>
            </div>

            <SCRIPT type=text/javascript>
                $(function(){
                    $(".jqzoom").jqueryzoom({
                        xzoom:400,
                        yzoom:400,
                        offset:10,
                        position:"right",
                        preload:1,
                        lens:1
                    });
                    $("#spec-list").jdMarquee({
                        deriction:"left",
                        width:350,
                        height:56,
                        step:2,
                        speed:4,
                        delay:10,
                        control:true,
                        _front:"#spec-right",
                        _back:"#spec-left"
                    });
                    $("#spec-list img").bind("mouseover",function(){
                        var src=$(this).attr("src");
                        $("#spec-n1 img").eq(0).attr({
                            src:src.replace("\/n5\/","\/n1\/"),
                            jqimg:src.replace("\/n5\/","\/n0\/")
                        });
                        $(this).css({
                            "border":"2px solid #ff6600",
                            "padding":"1px"
                        });
                    }).bind("mouseout",function(){
                        $(this).css({
                            "border":"1px solid #ccc",
                            "padding":"2px"
                        });
                    });
                })
            </SCRIPT>
            <!-- 京东商品展示 End -->

        </div>
        <div class="shop_goods_show_right">
            <ul>
                <li>
                    <strong style="font-size:14px; font-weight:bold;">${goods.subject}</strong>
                </li>
                <li>
                    <label>秒杀价：</label>
                    <span><strong>${goods.goodsSecondKillEntity.killPrice?string("￥#,###.##")}</strong>元</span>
                </li>
                <li>
                    <label>原价：</label>
                    <span><del>${goods.price?string("￥#,###.##")}</del>元</span>
                </li>
                <li>
                    <label>当前库存：</label>
                    <span>${goods.goodsSecondKillEntity.killSave}件</span>
                </li>
                <li>
                    <label>评价：</label>
                    <span>0条评论</span>
                </li>
                <li class="goods_num">
                    <label>购买数量：</label>
                    <span>
                        <#--<a class="good_num_jian" id="good_num_jian" href="javascript:void(0);"></a>-->
                        <input type="text" value="1" id="good_nums" class="good_nums" />
                        <#--<a href="javascript:void(0);" id="good_num_jia" class="good_num_jia"></a>(当前库存0件)-->
                    </span>
                </li>
                <li style="padding:20px 0;">
                    <label>&nbsp;</label>
                    <#--设置提醒我的额功能-->
                    <span>
                        <button id="tixing_me" alt="0">提醒我</button>
                    </span>
                    <span>
                        <strong style="color: red">倒计时：<span id="djs">00:00:00</span></strong>
                    </span>
                    <span>
                        <button id="gobtn" disabled="disabled">即将开始</button>
                    </span>
                </li>
            </ul>
        </div>
    </div>
    <!-- 商品展示 End -->
    <#--倒计时以及立即抢购-->
    <script type="text/javascript">

        //设置提醒我的功能
        $(function () {
            //绑定一个点击事件
            $("#tixing_me").click(tixing);

            //获取后台传过来的商品id
            var gid=${goods.id};
            //判断是否设置提醒
            //查询浏览器中cookie的值
            var result=getCookie("tixing_"+gid);
            if(result){
                $("#tixing_me").html("取消提醒");
                $("#tixing_me").attr("alt",1);
            }else {
                $("#tixing_me").html("提醒我");
                $("#tixing_me").attr("alt",0);
            }

        });

        /**
         * 提醒和取消提醒的功能
         * */
        function tixing() {
            //获取提醒按钮的值 默认值为0
            var flag=$("#tixing_me").attr("alt");

            //获取商品的id
            var gid=${goods.id};

            if(flag==0){  //还未提醒
                //将当前提醒的商品id存到浏览器cookie中
                setCookie("tixing_"+gid,true);

                //提醒完成
                $("#tixing_me").html("取消提醒");
                //将值设置为1
                $("#tixing_me").attr("alt",1);
                flag=1;
            }else {
                //取消提醒的操作
                //删除商品提醒的值
                delCookie("tixing_"+gid);
                //修改页面的按钮以及值
                $("#tixing_me").html("提醒我");
                $("#tixing_me").attr("alt",0);

                flag=0;
            }

            //通知后台进行消息的提醒与消息的取消提醒
            $.ajax({
                url:"/kill/tixing",
                data:{"gid":gid,"flag":flag},
                success: function (data) {
                    //后台设置成功提醒或者取消提醒成功
                    if(data.code=="200"){
                        alert("秒杀信息提醒:"+(flag==1? "设置":"取消")+"成功");
                    }
                }
            });
        }


        /**
         * 删除cookies
         * */
        function delCookie(name)
        {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval=getCookie(name);
            if(cval!=null)
                document.cookie= name + "="+cval+";expires="+exp.toGMTString();
        }



        /**
         * 浏览器获取cookie的方法
         * */
        function getCookie(name)
        {
            var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
            if(arr=document.cookie.match(reg))
                return unescape(arr[2]);
            else
                return null;
        }


        /**
         * 客户端设置cookie的方法
         * */
        function setCookie(name,value)
        {
            var Days = 30;
            var exp = new Date();
            exp.setTime(exp.getTime() + Days*24*60*60*1000);
            document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
        }




        
        //当前时间 后台服务器查询
        var now;
        //商品的开始抢购时间
        var startTime=new Date('${goods.goodsSecondKillEntity.startTime?datetime}');
        //计算器
        var count=0;
        //定时器对象
        var mytime;

        //页面加载事件
        $(function () {
            //获取时间
            getServerNow();
            //开始倒计时
            djs();
        });

        
        /**
         * 倒计时的方法‘
         *
         **/
        function djs() {
            //计算时间 商品的开始时间和当前时间进行计算
            var times=startTime.getTime()-now.getTime();
            console.log(times);
            console.log(startTime.getTime());
            console.log(now.getTime());

            //进行判断  抢购已经开始
            if(times<=0){
                //立即抢购 开放按钮
                goBtn();
                return;
            }

            //抢购没有开始进行倒计时
            //计算时分秒
            var h=parseInt(times/1000/60/60);//时
            var m=parseInt(times/1000/60%60);//分
            var s=parseInt(times/1000%60);//秒
            var ms=parseInt(times%1000);//毫秒

            //格式化时间
            var djstime=formatTime(h)+":"+formatTime(m)+":"+formatTime(s);
            $("#djs").html(djstime);

            //定时器对象实时更新服务的时间 一秒钟刷新一起时间的倒数
            mytime =setTimeout(function () {
                //计数器一分钟
                if(count>60){
                    //去服务器查询一次时间 减少误差
                    console.log("服务器查询了时间");
                    getServerNow();
                    //重置计数器
                    count=0;
                }else {
                    //秒数加一
                    now.setSeconds(now.getSeconds()+1);
                    //毫秒数
                    now.setMilliseconds(now.getMilliseconds()+ms);
                    //计数器自增
                    count++;

                }
                //递归调用
                djs();

            },1000+ms);//补回差掉的毫秒值
        }


        /**
         * 格式化当前时间
         *
         */
        function formatTime(n) {
            if(n<10){
                return "0"+n;
            }
            return n;
        }


        /**
         * 立即抢购的方法
         */
        function goBtn() {
            //按钮的点击的时间
            $("#gobtn").click(function () {
                //验证码的弹框
                $("#code_dialog").dialog( {
                    //弹框的参数设置
                    title:"请输入验证码",
                    width:200,
                    height:160,
                    modal:true
                });
            });

            //开放按钮
            $("#gobtn").removeAttr("disabled");
            $("#gobtn").html("开始抢购");
        }



        //获取当前服务器的时间 以服务的时间作为抢购的标准
        function getServerNow() {
            $.ajax({
                url:"/kill/queryNow",
                type:"get",
                async:false,//设置为异步的ajax为同步执行
                success:function (data) {
                    if(data.code=="200"){
                        now=new Date(data.data);
                    }
                }
            });
        }

        /**
         * 监听到浏览器的隐藏和显示时间
         */
        document.addEventListener('visibilitychange',function () {
            //进行状态的判断
            if(document.visibilityState=='hidden'){
                //停止定时器
                clearTimeout(mytime);
            }else{
                //浏览器重新可见
                //继续开始倒计时
                //获取当前时间
                getServerNow();
                djs();
            }
        });
        /**
         * 发送后台服务获取验证码
         */
        function update_code(img) {
            img.src="kill/code?t="+new Date().getTime();
        }

        /**
         * 抢购下单
         * 
         */
        function qiangGou() {
            //验证码验证
            var mycode=$("#mycode").val();
            location.href="/kill/qiangGou?gid=${goods.id}&code="+mycode;
        }

    </script>

    <#--验证码的弹框-->
    <div id="code_dialog" style="display: none">
        验证码:<input type="text" id="mycode" name="code" style="width: 60px;"/>
        <img src="/kill/code" onclick="update_code(this);"/><br/><br/>
        <button type="button" onclick="qiangGou()">立即抢购</button>
    </div>
    <div class="clear mt15"></div>
    <!-- Goods Left -->
    <div class="shop_bd_list_left clearfix">
        <!-- 左边商品分类 -->
        <div class="shop_bd_list_bk clearfix">
            <div class="title">商品分类</div>
            <div class="contents clearfix">
                <dl class="shop_bd_list_type_links clearfix">
                    <dt><strong>配件</strong></dt>
                    <dd>
                        <span><a >轮胎</a></span>
                        <span><a >发动机</a></span>
                        <span><a >气垫</a></span>
                        <span><a >后视镜</a></span>
                        <span><a >座椅</a></span>
                        <span><a >方向盘</a></span>
                        <span><a >雨刷</a></span>
                        <span><a >行车记录仪</a></span>
                        <span><a >后视镜</a></span>
                        <span><a >车毯</a></span>
                        <span><a >车载导航仪</a></span>
                        <span><a >语音播报器</a></span>
                    </dd>
                </dl>
            </div>
        </div>
        <!-- 左边商品分类 End -->
    </div>
    <!-- Goods Left End -->

    <!-- 商品详情 -->
    <script type="text/javascript" src="js/shop_goods_tab.js"></script>
    <div class="shop_goods_bd_xiangqing clearfix">
        <div class="shop_goods_bd_xiangqing_tab">
            <ul>
                <li id="xiangqing_tab_1" onmouseover="shop_goods_easytabs('1', '1');" onfocus="shop_goods_easytabs('1', '1');" onclick="return false;"><a ><span>商品详情</span></a></li>
                <li id="xiangqing_tab_2" onmouseover="shop_goods_easytabs('1', '2');" onfocus="shop_goods_easytabs('1', '2');" onclick="return false;"><a ><span>商品评论</span></a></li>
                <li id="xiangqing_tab_3" onmouseover="shop_goods_easytabs('1', '3');" onfocus="shop_goods_easytabs('1', '3');" onclick="return false;"><a ><span>商品咨询</span></a></li>
            </ul>
        </div>
        <div class="shop_goods_bd_xiangqing_content clearfix">
            <div id="xiangqing_content_1" class="xiangqing_contents clearfix">
                <p>商品详情----11111</p>
            </div>
            <div id="xiangqing_content_2" class="xiangqing_contents clearfix">
                <p>商品评论----22222</p>
            </div>

            <div id="xiangqing_content_3" class="xiangqing_contents clearfix">
                <p>商品自诩---3333</p>
            </div>
        </div>
    </div>
    <!-- 商品详情 End -->

</div>
<!-- Goods Body End -->

<!-- Footer - wll - 2013/3/24 -->
<div class="clear"></div>
<div class="shop_footer">
    <div class="shop_footer_link">
        <p>
            <a href="http://localhost">首页</a>|
            <a >招聘英才</a>|
            <a >广告合作</a>|
            <a >关于ShopCZ</a>|
            <a >关于我们</a>
        </p>
    </div>
    <div class="shop_footer_copy">
        <p>Copyright 2004-2013 itcast Inc.,All rights reserved.</p>
    </div>
</div>
<!-- Footer End -->

</body>
</html>