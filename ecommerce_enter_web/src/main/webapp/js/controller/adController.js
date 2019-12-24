//控制层
app.controller('adController' ,function($scope,$http,adService){

    // $controller('baseController',{$scope:$scope});//继承


    $scope.loginFlag=false;
    $scope.username='';
    //获取用户名
    $scope.getName=function(){
        adService.getName().success(
            function(response){
                $scope.loginFlag=response.success;
                if(response.success){
                    $scope.username=response.message;
                }
            }
        );
    }

    $scope.contentList=[];//广告集合 --- 1：首页轮播，2：发现好货，3：为你推荐
    $scope.findGoodList=[];

    //初始化加载（1,2,3）
    $scope.findByCategoryId=function(categoryId){
        // console.log(categoryId)
        adService.findByCategoryId(categoryId).success(
            function(response){
                $scope.contentList[categoryId]=response;
                // console.log($scope.contentList[categoryId])
                if(categoryId==2){
                    $scope.findGood();
                }
            }
        );
    }
    //发现好货，4个图一循环
    $scope.findGood=function(){
        for(var i=0;i<$scope.contentList[2].length/4;i++){
            $scope.findGoodList[i]=$scope.contentList[2].slice(i*4,
                (i*4+4)<$scope.contentList[2].length?(i*4+4):$scope.contentList[2].length );
        }
        // console.log($scope.findGoodList)

    }

    //搜索跳转
    $scope.search=function(){
        location.href="http://localhost:9104/ecommerce_search_web/#?keywords="+$scope.keywords;
    }


    // $scope.cartList=[]
    //查询购物车列表
    $scope.findCartList=function(){
        $http.get('http://localhost:9107/ecommerce_cart_web/cart/findCartList.do',
            {'withCredentials':true}).success(
            function(response){
                $scope.cartList=response;
                $scope.totalValue= $scope.sum(response);
                console.log($scope.cartList)
                console.log($scope.totalValue)
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
    //商品详情地址
    $scope.getUrl=function(goodsId){
        return "http://localhost:9105/ecommerce_html_web/admin/"+goodsId+".html";
    }
    //数量加减
    $scope.addGoodsToCartList=function(itemId,num){
        $http.get('http://localhost:9107/ecommerce_cart_web/cart/addGoodsToCartList.do?itemId='+itemId+'&num='+num,
            {'withCredentials':true}).success(
            function(response){
                if(response.success){//如果成功
                    $scope.findCartList();//刷新列表
                }else{
                    alert(response.message);
                }
            }
        );
    }

});
