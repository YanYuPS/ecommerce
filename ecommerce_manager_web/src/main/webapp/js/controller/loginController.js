app.controller("loginController",function ($scope,$controller,sellerService,goodsService,loginService) {
    $controller("baseController",{$scope,$scope})

    $scope.showLoginName=function () {
        // console.log("showLoginName")
        loginService.loginName().success(
            function (response) {
                // console.log(response)
                $scope.loginName=response.loginName;
                $scope.dateTime=response.dateTime;
            }
        )
    }

    //待审核商家
    $scope.searchEntity_shop={status:'0'};//定义搜索对象(待审核)
    $scope.search_shop=function(page,rows){
        sellerService.searchAll($scope.searchEntity_shop).success(
            function(response){
                $scope.list_shop=response.rows;
                $scope.totalItems_shop=response.total;//总数
                // console.log($scope.list_shop)
            }
        );
    }

    //待审核商品
    $scope.searchEntity_goods={auditStatus:'1'};//定义搜索对象(待审核)
    $scope.search_goods=function(page,rows){
        goodsService.searchAll($scope.searchEntity_goods).success(
            function(response){
                $scope.list_goods=response.rows;
                $scope.totalItems_goods=response.total;//总数
                // console.log($scope.list_goods)
            }
        );
    }

})