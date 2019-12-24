<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <title>商品详情页</title>
    <#--<link rel="icon" href="../img/favicon.ico">-->

    <link rel="stylesheet" type="text/css" href="../css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="../css/pages-item.css"/>
    <link rel="stylesheet" type="text/css" href="../css/pages-zoom.css"/>
    <link rel="stylesheet" type="text/css" href="../css/widget-cartPanelView.css"/>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="../plugins/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="../plugins/bower_components/Ionicons/css/ionicons.min.css">
    <!-- jvectormap -->
    <link rel="stylesheet" href="../plugins/bower_components/jvectormap/jquery-jvectormap.css">


    <script type="text/javascript" src="../plugins/angularjs/angular.min.js"></script>

    <script type="text/javascript" src="../js/base.js"></script>
    <script type="text/javascript" src="../js/controller/baseController.js"></script>
    <script type="text/javascript" src="../js/controller/goodsController.js"></script>
    <script type="text/javascript" src="../js/service/userService.js"></script>


    <script>
        <#--  -->
        var skuList = [
            <#list itemList as item>
            {
                id:${item.id?c},
                title: '${item.title}',
                price:${item.price?c},
                spec:${item.spec}
            },
            </#list>
        ];

    </script>
</head>

<#--num:初始化数量-->
<body ng-app="ecommerce" ng-controller="goodsController"
      ng-init="getName();findCartList();num=1;loadSku();">

<!--页面顶部 开始-->
<#include "head.ftl">
<#--
    assign：定义
    eval：json字符串转对象
-->
<#--图片列表-->
<#assign imageList=goodsDesc.itemImages?eval>
<#--扩展属性-->
<#assign customAttributeList=goodsDesc.customAttributeItems?eval>
<#--规格-->
<#assign specificationList=goodsDesc.specificationItems?eval>
<!--页面顶部 结束-->



<#--没有实现的部分，自定义参数-->

<#--相关分类-->
<#assign xgflList=[{"name":"xxx"},{"name":"xxx"},{"name":"xxx"},
{"name":"xxx"},{"name":"xxx"},{"name":"xxx"}
]>
<#--相关分类的商品展示-->
<#assign xgflGoodsList=[
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"}
]>
<#--推荐搭配商品-->
<#assign dpList=[{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"}
]>
<#--猜你喜欢商品-->
<#assign likeList=[{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"},
{"title":"xxx","price":0,"img":"http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"}
]>



<div class="py-container">


    <div id="item">

        <#--分类-->
        <div class="crumb-wrap">
            <ul class="sui-breadcrumb">
                <#-- x?? 判断是不是null-->
                <li><a href="http://localhost:9104/ecommerce_search_web/#?keywords=${itemCat1}" target="_blank"><#if itemCat1??>${itemCat1}</#if></a></li>
                <li><a href="http://localhost:9104/ecommerce_search_web/#?keywords=${itemCat2}" target="_blank"><#if itemCat2??>${itemCat2}</#if></a></li>
                <li><a href="http://localhost:9104/ecommerce_search_web/#?keywords=${itemCat3}" target="_blank"><#if itemCat3??>${itemCat3}</#if></a></li>
            </ul>
        </div>

        <!--商品展示-->
        <div class="product-info">
            <#--图片-->
            <div class="fl preview-wrap">
                <!--放大镜效果-->
                <div class="zoom">
                    <!--默认第一个预览-->
                    <div id="preview" class="spec-preview">
                        <span class="jqzoom">
                            <#--
                                a?xxx : a的内置方法
                            -->
                            <#if (imageList?size>0)>
                                <img jqimg="${imageList[0].url}" src="${imageList[0].url}" width="400px"
                                     height="400px"/>
                            </#if>
                        </span>
                    </div>
                    <!--下方的缩略图-->
                    <div class="spec-scroll">
                        <a class="prev">&lt;</a>
                        <!--左右按钮-->
                        <div class="items">
                            <ul>
                                <#--
                                    aList as a : 循环list
                                -->
                                <#list imageList as item>
                                    <li><img src="${item.url}" bimg="${item.url}" onmousemove="preview(this)"/></li>
                                </#list>
                            </ul>
                        </div>
                        <a class="next">&gt;</a>
                    </div>
                </div>
            </div>

            <div class="fr itemInfo-wrap">
                <div class="sku-name">
                    <h4>{{sku.title}}</h4>
                </div>
                <div class="news"><span>${goods.caption} </span></div>
                <div class="summary">
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>价　　格</i>
                        </div>
                        <div class="fl price">
                            <i>¥</i>
                            <em>{{sku.price}}</em>
                            <span style="color:#ccc">降价通知</span>
                        </div>
                        <div class="fr remark">
                            <i>累计评价</i><em>xxx</em>
                        </div>
                    </div>
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>促　　销</i>
                        </div>
                        <div class="fl fix-width">
                            <i class="red-bg">xxx</i>
                            <em class="t-gray">xxx</em>
                        </div>
                    </div>
                </div>
                <div class="support">
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>增值业务</i>
                        </div>
                        <div class="fl fix-width">
                            <em class="t-gray">xxx</em>
                        </div>
                    </div>
                    <div class="summary-wrap">
                        <div class="fl title">
                            <i>配 送 至</i>
                        </div>
                        <div class="fl fix-width">
                            <em class="t-gray">xxx</em>
                        </div>
                    </div>
                </div>
                <div class="clearfix choose">
                    <#--规格-->
                    <div id="specification" class="summary-wrap clearfix">
                        <#list specificationList as spec>
                            <dl>
                                <#--规格名-->
                                <dt>
                                    <div class="fl title">
                                        <i>${spec.attributeName}</i>
                                    </div>
                                </dt>
                                <#--规格信息-->
                                <#list spec.attributeValue as item>
                                    <dd>
                                        <a href="javascript:;"
                                           class="{{isSelected('${spec.attributeName}','${item}')?'selected':''}}"
                                           ng-click="selectSpecification('${spec.attributeName}','${item}')">${item}
                                            <span title="点击取消选择">&nbsp;</span></a>
                                    </dd>
                                </#list>
                            </dl>
                        </#list>
                    </div>
                    <div class="summary-wrap">
                        <#--商品数量-->
                        <div class="fl title">
                            <div class="control-group">
                                <div class="controls">
                                    <input autocomplete="off" ng-model="num" type="text" value="1" minnum="1"
                                           class="itxt"/>
                                    <a href="javascript:void(0)" class="increment plus" ng-click="addNum(1)">+</a>
                                    <a href="javascript:void(0)" class="increment mins" ng-click="addNum(-1)">-</a>
                                </div>
                            </div>
                        </div>
                        <div class="fl">
                            <ul class="btn-choose unstyled">
                                <li>
                                    <a class="sui-btn  btn-danger addshopcar" ng-click="addToCart()">加入购物车</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--product-detail-->
        <div class="clearfix product-detail">

            <#--左侧推荐-->
            <div class="fl aside">
                <ul class="sui-nav nav-tabs tab-wraped">
                    <li class="active">
                        <a href="#index" data-toggle="tab">
                            <span>相关分类</span>
                        </a>
                    </li>
                    <li>
                        <a href="#profile" data-toggle="tab">
                            <span>推荐品牌</span>
                        </a>
                    </li>
                </ul>
                <div class="tab-content tab-wraped">
                    <div id="index" class="tab-pane active">

                        <#--相关分类-->
                        <ul class="part-list unstyled">
                            <#list xgflList as list>
                                <#--{"name":"xxx"}-->
                                <#--<#list list as key,value>-->
                                    <li>${list["name"]}</li>
                                <#--</#list>-->
                            </#list>
                        </ul>

                        <#--分类商品推荐-->
                        <ul class="goods-list unstyled">
                            <#list xgflGoodsList as list>
                                <#--{"title":"xxx","price":0,"img":}-->
                                <li>
                                    <div class="list-wrap">
                                        <div class="p-img">
                                            <img src="${list["img"]}" style="width:80%"/>
                                        </div>
                                        <div class="attr">
                                            <em>${list["title"]}</em>
                                        </div>
                                        <div class="price">
                                            <strong>
                                                <em>¥</em>
                                                <i>${list["price"]}</i>
                                            </strong>
                                        </div>
                                        <div class="operate">
                                            <a href="javascript:void(0);" class="sui-btn btn-bordered">加入购物车</a>
                                        </div>
                                    </div>
                                </li>
                            </#list>
                        </ul>
                    </div>

                    <div id="profile" class="tab-pane">
                        <p>推荐品牌</p>
                    </div>
                </div>
            </div>

            <#--中间推荐-->
            <div class="fr detail">
                <div class="clearfix fitting">
                    <h4 class="kt">选择搭配</h4>
                    <div class="good-suits">

                        <div class="fl master">
                            <div class="list-wrap">
                                <div class="p-img">
                                    <#if (imageList?size>0)>
                                        <img src="${imageList[0].url}" style="width:100px;height:120px;"/>
                                    </#if>
                                    <!--<img src="../img/_/l-m01.png"/>-->
                                </div>
                                <em>￥{{sku.price}}</em>
                                <i>+</i>
                            </div>
                        </div>

                        <div class="fl suits">
                            <ul class="suit-list">
                                <#list dpList as list>
                                    <li class="">
                                        <div id="">
                                            <img src="${list["img"]}" style="width:100px;height:120px;"/>
                                        </div>
                                        <i>${list["title"]}</i>
                                        <label data-toggle="checkbox" class="checkbox-pretty">
                                            <input type="checkbox"><span>${list["price"]}</span>
                                        </label>
                                    </li>
                                </#list>
                            </ul>
                        </div>

                        <div class="fr result">
                            <div class="num">已选购x件商品</div>
                            <div class="price-tit"><strong>套餐价</strong></div>
                            <div class="price">￥x</div>
                            <button class="sui-btn  btn-danger addshopcar">加入购物车</button>
                        </div>
                    </div>
                </div>


                <!-- 商品详细信息 -->
                <div class="tab-main intro">
                    <ul class="sui-nav nav-tabs tab-wraped">
                        <li class="active">
                            <a href="#one" data-toggle="tab">
                                <span>商品介绍</span>
                            </a>
                        </li>
                        <li>
                            <a href="#two" data-toggle="tab">
                                <span>规格与包装</span>
                            </a>
                        </li>
                        <li>
                            <a href="#three" data-toggle="tab">
                                <span>售后保障</span>
                            </a>
                        </li>
                        <li>
                            <a href="#four" data-toggle="tab">
                                <span>商品评价</span>
                            </a>
                        </li>
                        <li>
                            <a href="#five" data-toggle="tab">
                                <span>手机社区</span>
                            </a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>

                    <#--扩展属性-->
                    <div class="tab-content tab-wraped">
                        <div id="one" class="tab-pane active">
                            <ul class="goods-intro unstyled">
                                <#list customAttributeList as item>
                                    <#if item.value??>
                                        <li>${item.text}：${item.value}</li>
                                    </#if>
                                </#list>
                            </ul>
                            <div class="intro-detail">
                                ${goodsDesc.introduction}
                            </div>
                        </div>
                        <div id="two" class="tab-pane">
                            <p>${goodsDesc.packageList}</p>
                        </div>
                        <div id="three" class="tab-pane">
                            <p>${goodsDesc.saleService}</p>
                        </div>
                        <div id="four" class="tab-pane">
                            <p>商品评价</p>
                        </div>
                        <div id="five" class="tab-pane">
                            <p>手机社区</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <!--like-->
        <div class="clearfix"></div>
        <div class="like">
            <h4 class="kt">猜你喜欢</h4>
            <div class="like-list">
                <ul class="yui3-g">
                    <#list likeList as list>
                        <li class="yui3-u-1-6">
                            <div class="list-wrap">
                                <div class="p-img">
                                    <img src="${list["img"]}" style="width:80%"/>
                                </div>
                                <div class="attr">
                                    <em>${list["title"]}</em>
                                </div>
                                <div class="price">
                                    <strong>
                                        <em>¥</em>
                                        <i>${list["price"]}</i>
                                    </strong>
                                </div>
                                <div class="commit">
                                    <i class="command">已有0人评价</i>
                                </div>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>


    </div>
</div>




<!--页面底部  开始 -->
<#include "foot.ftl">
<!--页面底部  结束 -->
</body>

</html>