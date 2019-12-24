<div class="clearfix footer">

<!--页面底部-->
<!--<div class="clearfix footer">-->
<div class="py-container">
    <div class="footlink">

        <div class="Mod-service">
            <ul class="Mod-Service-list">
                <li class="grid-service-item intro  intro1">
                    <i class="serivce-item fl"></i>
                    <div class="service-text">
                        <h4>品类齐全，轻松购物</h4>
                    </div>
                </li>
                <li class="grid-service-item  intro intro2">
                    <i class="serivce-item fl"></i>
                    <div class="service-text">
                        <h4>多仓直发，极速配送</h4>
                    </div>
                </li>
                <li class="grid-service-item intro  intro3">
                    <i class="serivce-item fl"></i>
                    <div class="service-text">
                        <h4>正品行货，精致服务</h4>
                    </div>
                </li>
                <li class="grid-service-item  intro intro4">
                    <i class="serivce-item fl"></i>
                    <div class="service-text">
                        <h4>天天低价，畅选无忧</h4>
                    </div>
                </li>
            </ul>
        </div>

        <div class="clearfix Mod-list">
            <div class="yui3-g">
                <div class="yui3-u-1-6">
                    <h4>购物指南</h4>
                    <ul class="unstyled">
                        <li>购物流程</li>
                        <li>会员介绍</li>
                        <li>生活旅行</li>
                        <li>常见问题</li>
                        <li>大家电</li>
                        <li>联系客服</li>
                    </ul>
                </div>
                <div class="yui3-u-1-6">
                    <h4>配送方式</h4>
                    <ul class="unstyled">
                        <li>上门自提</li>
                        <li>211限时达</li>
                        <li>配送服务查询</li>
                        <li>配送费收取标准</li>
                        <li>海外配送</li>
                    </ul>
                </div>
                <div class="yui3-u-1-6">
                    <h4>支付方式</h4>
                    <ul class="unstyled">
                        <li>货到付款</li>
                        <li>在线支付</li>
                        <li>分期付款</li>
                        <li>公司转账</li>
                    </ul>
                </div>
                <div class="yui3-u-1-6">
                    <h4>售后服务</h4>
                    <ul class="unstyled">
                        <li>售后政策</li>
                        <li>价格保护</li>
                        <li>退款说明</li>
                        <li>返修/退换货</li>
                        <li>取消订单</li>
                    </ul>
                </div>
                <div class="yui3-u-1-6">
                    <h4>特色服务</h4>
                    <ul class="unstyled">
                        <li>夺宝岛</li>
                        <li>DIY装机</li>
                        <li>延保服务</li>
                        <li>YE卡</li>
                        <li>Y通信</li>
                    </ul>
                </div>
                <div class="yui3-u-1-6">
                    <h4>Y自营覆盖区县</h4>
                    <ul class="unstyled">
                        <li>Y已向全国xxx个区县提供自营配送服务，支持货到付款、POS机刷卡和售后上门服务。</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="Mod-copyright">
            <ul class="helpLink">
                <li>关于我们<span class="space"></span></li>
                <li>联系我们<span class="space"></span></li>
                <li>联系客服<span class="space"></span></li>
                <li>合作招商<span class="space"></span></li>
                <li>商家帮助<span class="space"></span></li>
                <li>营销中心<span class="space"></span></li>
                <li>手机Y<span class="space"></span></li>
                <li>友情链接<span class="space"></span></li>
                <li>销售联盟<span class="space"></span></li>
                <li>Y社区<span class="space"></span></li>
                <li>风险监测<span class="space"></span></li>
                <li>隐私政策<span class="space"></span></li>
                <li>Y公益<span class="space"></span></li>
                <li>English Site<span class="space"></span></li>
                <li>Media & IR</li>
            </ul>
            <p></p>
            <p></p>
        </div>
    </div>
</div>
<!--</div>-->
<!--页面底部END-->

</div>



<!--侧栏面板开始-->
<div class="J-global-toolbar">
    <div class="toolbar-wrap J-wrap">
        <div class="toolbar">
            <div class="toolbar-panels J-panel">

                <!-- 购物车面板内容 -->
                <div style="visibility: hidden;" class="J-content toolbar-panel tbar-panel-cart toolbar-animate-out">
                    <h3 class="tbar-panel-header J-panel-header">
                        <!--标题-->
                        <a href="javascript:;" class="title"><i></i><em class="title">购物车</em></a>
                        <!--关闭按钮-->
                        <span class="close-panel J-close" onclick="cartPanelView.tbar_panel_close('cart');"></span>
                    </h3>
                    <div class="tbar-panel-main">
                        <div id="J-cart-tips" class="tbar-tipbox" ng-if="!loginFlag">
                            <div class="tip-inner">
                                <span class="tip-text">还没有登录，登录后商品将被保存</span>
                                <a href="#none" class="tip-btn J-login">登录</a>
                            </div>
                        </div>
                        <div class="tbar-panel-content J-panel-content">
                            <div id="J-cart-render">
                                <!-- 购物车商品列表 -->
                                <div id="cart-list" class="tbar-cart-list">
                                    <div class="tbar-cart-item">
                                        <!--满减提示-->
                                        <!--<div class="jtc-item-promo">
                                            <em class="promo-tag promo-mz"> <i class="arrow"></i> </em>
                                            <div class="promo-text"> </div>
                                        </div>-->
                                        <!--列表-->
                                        <div class="jtc-item-goods" ng-repeat="cart in cartList">
                                            <div ng-repeat="item in cart.orderItemList">
                                                <span class="p-img">
                                                    <!--<a href="javascript:;" target="_blank">aaa<img src="{2}" alt="{1}" height="50" width="50"/></a>-->
                                                    <a href="{{getUrl(item.goodsId)}}" target="_blank">
                                                        <img src="{{item.picPath}}" height="50" width="50"/>
                                                    </a>
                                                </span>
                                                <div class="p-name">
                                                    <a href="{{getUrl(item.goodsId)}}" target="_blank">{{item.title}}</a>
                                                </div>
                                                <div class="p-price">
                                                    <strong>¥{{item.price.toFixed(2)}}</strong>×{{item.num}}
                                                </div>
                                                <a href="javascript:;" ng-click="addGoodsToCartList(item.itemId,-item.num)" class="p-del J-del">删除</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="cart-footer" class="tbar-panel-footer J-panel-footer">
                        <div class="tbar-checkout">
                            <div class="jtc-number"><strong class="J-count" id="cart-number">{{totalValue.totalNum}}</strong>件商品</div>
                            <div class="jtc-sum"> 共计：<strong class="J-total" id="cart-sum">¥{{totalValue.totalMoney}}</strong></div>
                            <a class="jtc-btn J-btn" href="http://localhost:9107/ecommerce_cart_web/" target="_blank">去购物车结算</a>
                        </div>
                    </div>
                </div>

                <!-- 我的关注面板内容 -->
                <div style="visibility: hidden;" data-name="follow" class="J-content toolbar-panel tbar-panel-follow">
                    <h3 class="tbar-panel-header J-panel-header">
                        <a href="javascript:;" target="_blank" class="title"> <i></i> <em class="title">我的关注</em> </a>
                        <span class="close-panel J-close" onclick="cartPanelView.tbar_panel_close('follow');"></span>
                    </h3>
                    <div class="tbar-panel-main">
                        <div class="tbar-panel-content J-panel-content">
                            <div id="J-follow-render">
                                <!-- 我的关注商品列表 -->
                                <div id="follow-list" class="tbar-follow-list"></div>
                            </div>
                        </div>
                    </div>
                    <div id="follow-footer" class="tbar-panel-footer J-panel-footer">

                    </div>
                </div>

                <!-- 我的足迹面板内容 -->
                <div style="visibility: hidden;" class="J-content toolbar-panel tbar-panel-history toolbar-animate-in">
                    <h3 class="tbar-panel-header J-panel-header">
                        <a href="javascript:;" target="_blank" class="title"> <i></i> <em class="title">我的足迹</em> </a>
                        <span class="close-panel J-close" onclick="cartPanelView.tbar_panel_close('history');"></span>
                    </h3>
                    <div class="tbar-panel-main">
                        <div class="tbar-panel-content J-panel-content">
                            <div id="J-history-render">
                                <!-- 我的关注商品列表 -->
                                <div id="history-list" class="tbar-history-list"></div>
                            </div>

                            <div class="jt-history-wrap">
                                <ul>
                                    <!--<li class="jth-item">
                                    <a href="javascript:;" class="img-wrap"> <img src=".portal/img/like_03.png" height="100" width="100" /> </a>
                                    <a class="add-cart-button" href="javascript:;" target="_blank">加入购物车</a>
                                    <a href="javascript:;" target="_blank" class="price">￥498.00</a>
                                </li>
                                <li class="jth-item">
                                    <a href="javascript:;" class="img-wrap"> <img src="portal/img/like_02.png" height="100" width="100" /></a>
                                    <a class="add-cart-button" href="javascript:;" target="_blank">加入购物车</a>
                                    <a href="javascript:;" target="_blank" class="price">￥498.00</a>
                                </li>-->
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="tbar-panel-footer J-panel-footer">
                        <a href="javascript:;" class="history-bottom-more" target="_blank">查看更多足迹商品 &gt;&gt;</a>
                    </div>
                </div>

            </div>

            <div class="toolbar-header"></div>

            <!-- 侧栏按钮 -->
            <div class="toolbar-tabs J-tab">
                <div onclick="cartPanelView.tabItemClick('cart')" class="toolbar-tab tbar-tab-cart" data="购物车" tag="cart">
                    <i class="tab-ico"></i>
                    <em class="tab-text"></em>
                    <span class="tab-sub J-count " id="tab-sub-cart-count">{{totalValue.totalNum}}</span>
                </div>
                <div onclick="cartPanelView.tabItemClick('follow')" class="toolbar-tab tbar-tab-follow" data="我的关注" tag="follow">
                    <i class="tab-ico"></i>
                    <em class="tab-text"></em>
                    <span class="tab-sub J-count hide">0</span>
                </div>
                <div onclick="cartPanelView.tabItemClick('history')" class="toolbar-tab tbar-tab-history" data="我的足迹" tag="history">
                    <i class="tab-ico"></i>
                    <em class="tab-text"></em>
                    <span class="tab-sub J-count hide">0</span>
                </div>
            </div>

            <div class="toolbar-mini"></div>

        </div>

        <div id="J-toolbar-load-hook"></div>

    </div>
</div>
<!--侧栏面板结束-->




<script type="text/javascript" src="../js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#service").hover(function () {
            $(".service").show();
        }, function () {
            $(".service").hide();
        });
        $("#shopcar").hover(function () {
            $("#shopcarlist").show();
        }, function () {
            $("#shopcarlist").hide();
        });

    })
</script>
<script type="text/javascript" src="../js/model/cartModel.js"></script>
<script type="text/javascript" src="../js/plugins/jquery.easing/jquery.easing.min.js"></script>
<script type="text/javascript" src="../js/plugins/sui/sui.min.js"></script>
<script type="text/javascript" src="../js/plugins/jquery.jqzoom/jquery.jqzoom.js"></script>
<script type="text/javascript" src="../js/plugins/jquery.jqzoom/zoom.js"></script>
<#--<script type="text/javascript" src="index/index.js"></script>-->
<script type="text/javascript" src="../js/czFunction.js"></script>
<script type="text/javascript" src="../js/widget/cartPanelView.js"></script>
<script type="text/javascript" src="../js/widget/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../js/widget/nav.js"></script>

