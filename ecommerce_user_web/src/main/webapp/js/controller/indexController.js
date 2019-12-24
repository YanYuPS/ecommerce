//首页控制器
app.controller('indexController',function($scope,$http,loginService){

    $scope.loginFlag=false;
    $scope.loginName='';
    //获取用户名
    $scope.showName=function(){
        loginService.showName().success(
            function(response){
                $scope.loginFlag=response.success;
                if(response.success){
                    $scope.loginName=response.message;
                }
            }
        );
    }

    //搜索跳转
    // $scope.keywords=''
    $scope.search=function(){
        location.href="http://localhost:9104/ecommerce_search_web/#?keywords="+$scope.keywords;
    }


    //查询购物车列表
    $scope.findCartList=function(){
        // console.log(111)
        $http.get('http://localhost:9107/ecommerce_cart_web/cart/findCartList.do',
            {'withCredentials':true}).success(
            function(response){
                // $scope.cartList=response;
                $scope.totalValue= $scope.sum(response);
                // console.log($scope.totalValue)
            }
        );
    }
    //求合计数
    $scope.sum=function(cartList){
        var totalValue={totalNum:0,totalMoney:0 };

        for(var i=0;i<cartList.length ;i++){
            var cart=cartList[i];//购物车对象
            for(var j=0;j<cart.orderItemList.length;j++){
                var orderItem=  cart.orderItemList[j];//购物车明细
                totalValue.totalNum+=orderItem.num;//累加数量
                totalValue.totalMoney+=orderItem.totalFee;//累加金额
            }
        }
        return totalValue;
    }

});
