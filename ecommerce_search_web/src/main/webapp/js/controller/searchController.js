app.controller('searchController',function($scope,$http,$location,userService,searchService){

    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'',
        'pageNo':1,'pageSize':10, 'sortField':'','sort':'' };//搜索对象

    $scope.resultMap={total: 0, totalPages: 1} //搜索结果

    //搜索结果处 展示的 搜索关键字
    $scope.searchWord=''

    //价格筛选列表
    $scope.priceList=['0-1000','1000-2000','2000-3000','3000-4000']

    //价格排序
    // $scope.priceSort='DESC'

    // $scope.item.image="http://172.16.212.21/group1/M00/00/00/rBDUFV3n1yyAL9L0AABCe5fWYMs475.jpg"//图片初始化


    ///---------------------------------------------------------------------------------------
    $scope.loginFlag=false;
    $scope.username='';
    //获取用户名
    $scope.getName=function(){
        userService.getName().success(
            function(response){
                $scope.loginFlag=response.success;
                if(response.success){
                    $scope.username=response.message;
                }
            }
        );
    }

    //查询购物车列表
    $scope.findCartList=function(){
        // console.log(111)
        $http.get('http://localhost:9107/ecommerce_cart_web/cart/findCartList.do',
            {'withCredentials':true}).success(
            function(response){
                $scope.cartList=response;
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
    ///--------------------------------------------------------------------------------


    //全部
    $scope.searchAll=function(){
        searchService.searchAll().success(
            function(response){
                $scope.resultMap=response;
            }
        );
    }

    //加载查询字符串
    $scope.loadKeywords=function(){
        $scope.searchMap.keywords=  $location.search()['keywords'];
        if($scope.searchMap.keywords!=null){
            $scope.search();
        }

    }

    //搜索
    $scope.search=function(){
        $scope.searchMap.pageNo= parseInt($scope.searchMap.pageNo);

        if($scope.searchMap.keywords!=null && $scope.searchMap.keywords!="") {
            $scope.searchWord = $scope.searchMap.keywords

            searchService.search($scope.searchMap).success(
                function(response){
                    $scope.resultMap=response;
                    // console.log($scope.resultMap)
                    buildPageLabel();//构建分页标签
                }
            );
        }else {
            console.log("搜索关键词为空")
        }
    }

    //根据页码查询
    $scope.queryByPage=function(pageNo){
        //页码验证
        if(pageNo<1 || pageNo>$scope.resultMap.totalPages){
            return;
        }
        $scope.searchMap.pageNo=pageNo;
        $scope.search();
    }


    //添加搜索项
    $scope.addSearchItem=function(key,value){
        if(key=='category' || key=='brand' || key=='price'){//如果点击的是分类或者是品牌
            $scope.searchMap[key]=value;
            // console.log("add")
        }else{
            $scope.searchMap.spec[key]=value;
        }

        $scope.search();//执行搜索
    }

    //移除复合搜索条件
    $scope.removeSearchItem=function(key){
        if(key=="category" ||  key=="brand" || key=='price'){//如果是分类或品牌
            $scope.searchMap[key]="";
        }else{//否则是规格
            delete $scope.searchMap.spec[key];//移除此属性
        }

        $scope.search();//执行搜索
    }

    //构建分页标签(totalPages为总页数)
    buildPageLabel=function(){
        $scope.pageLabel=[];//新增分页栏属性

        var maxPageNo= $scope.resultMap.totalPages;//得到最后页码

        var firstPage=1;//开始页码
        var lastPage=maxPageNo;//截止页码

        $scope.firstDot=true;//前面有点
        $scope.lastDot=true;//后边有点

        //确定页码
        if($scope.resultMap.totalPages> 5){  //如果总页数大于5页,显示部分页码
            if($scope.searchMap.pageNo<=3){//如果当前页小于等于3，显示前5页
                lastPage=5; //前5页
                $scope.firstDot=false;//前面没点
            }else if( $scope.searchMap.pageNo>=lastPage-2  ){//如果当前页大于等于最大页码-2，显示后5页
                firstPage= maxPageNo-4;		 //后5页
                $scope.lastDot=false;//后面没点
            }else{ //显示当前页为中心的5页（前2，后2）
                firstPage=$scope.searchMap.pageNo-2;
                lastPage=$scope.searchMap.pageNo+2;
            }
        }else{
            $scope.firstDot=false;
            $scope.lastDot=false;
        }

        //循环产生页码标签
        for(var i=firstPage;i<=lastPage;i++){
            $scope.pageLabel.push(i);
        }
    }


    //判断当前页为第一页
    $scope.isTopPage=function(){
        if($scope.searchMap.pageNo==1){
            return true;
        }else{
            return false;
        }
    }

    //判断当前页是否未最后一页
    $scope.isEndPage=function(){
        if($scope.searchMap.pageNo==$scope.resultMap.totalPages){
            return true;
        }else{
            return false;
        }
    }

    //设置排序规则
    $scope.sortSearch=function(sortField,sort){

        //价格排序处理
        // $scope.priceSort=sort

        $scope.searchMap.sortField=sortField;
        $scope.searchMap.sort=sort;
        $scope.search();
    }

    //判断关键字是不是品牌
    $scope.keywordsIsBrand=function(){
        for(var i=0;i<$scope.resultMap.brandList.length;i++){
            if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text)>=0){//如果包含
                return true;
            }
        }
        return false;
    }

    //排序选项
    $scope.filterFlag=1
    $scope.changeFilterFlag=function (flag) {
        $scope.filterFlag=flag;
    }


});