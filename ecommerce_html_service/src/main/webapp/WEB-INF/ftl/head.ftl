<div id="nav-bottom">
    <div class="nav-top">
        <!--顶部菜单栏-->
        <div class="top">
            <div class="py-container">
                <div class="shortcut">
                    <ul class="fr">
                        <li class="f-item" ng-if="!loginFlag">
                            <a href="../jump.html" >你好，请登录</a>　
                            <span><a href="http://localhost:9106/ecommerce_user_web/admin/register.html" target="_blank">免费注册</a></span>
                        </li>
                        <li class="f-item" ng-if="loginFlag">
                            <span style="color: #000;">你好，{{username}}</span>
                            <span class="space"></span>
                            <span><a href="logout/cas">退出登录 </a></span>
                        </li>
                        <li class="f-item space"></li>

                        <li class="f-item">
                            <a href="http://localhost:9106/ecommerce_user_web/" >我的订单</a>
                        </li>
                        <li class="f-item space"></li>

                        <li class="f-item">我的Y</li>
                        <li class="f-item space"></li>

                        <li class="f-item">Y会员</li>
                        <li class="f-item space"></li>

                        <li class="f-item">企业采购</li>
                        <li class="f-item space"></li>

                        <li class="f-item">客户服务</li>
                        <li class="f-item space"></li>

                        <li class="f-item">网站导航</li>
                        <li class="f-item space"></li>

                        <li class="f-item">手机Y</li>
                    </ul>
                </div>
            </div>
        </div>

        <!--头部-->
        <div class="header">
            <div class="py-container">
                <div class="yui3-g Logo">
                    <!--图标-->
                    <div class="yui3-u Left ">
                        <a class="logo-bd" title="Y" href="http://localhost:9103/ecommerce_enter_web/" ></a>
                    </div>
                    <div class="yui3-u Center searchArea">
                        <!--搜索-->
                        <div class="search">
                            <form action="" class="sui-form form-inline">
                                <!--searchAutoComplete-->
                                <div class="input-append">
                                    <input type="text" id="autocomplete" type="text" class="input-error input-xxlarge"
                                           ng-model="keywords"/>
                                    <button class="sui-btn btn-xlarge btn-danger" ng-click="search()" type="button">搜索
                                    </button>
                                </div>
                            </form>
                        </div>
                        <!--搜索框下的链接-->
                        <div class="hotwords">
                            <ul>
                                <li class="f-item">美妆好礼</li>
                                <li class="f-item">199减20</li>
                                <li class="f-item">圣诞好礼</li>
                                <li class="f-item">Y国际</li>
                                <li class="f-item">每100-50</li>
                                <li class="f-item">ROG手机</li>
                            </ul>
                        </div>
                    </div>
                    <!--购物车-->
                    <div class="yui3-u Right shopArea">
                        <div class="fr shopcar">
                            <div class="show-shopcar" id="shopcar">
                                <span class="car"></span>
                                <a class="sui-btn btn-default btn-xlarge" href="http://localhost:9107/ecommerce_cart_web/" target="_blank">
                                    <span>我的购物车</span>
                                    <i class="shopnum">{{totalValue.totalNum}}</i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="yui3-g NavList">
                    <div class="yui3-u Left"></div>
                    <div class="yui3-u Center navArea">
                        <ul class="nav">
                            <li class="f-item"><a href="http://localhost:9109/ecommerce_seckill_web/" target="_blank">秒杀</a></li>
                            <li class="f-item">优惠券</li>
                            <li class="f-item">会员</li>
                            <li class="f-item">品牌闪购</li>
                            <li class="f-item">拍卖</li>
                            <li class="f-item">时尚</li>
                            <li class="f-item">超市</li>
                            <li class="f-item">生鲜</li>
                            <!--<li class="f-item">国际</li>-->
                            <!--<li class="f-item">金融</li>-->
                        </ul>
                    </div>
                    <div class="yui3-u Right"></div>
                </div>
            </div>
        </div>
    </div>
</div>
