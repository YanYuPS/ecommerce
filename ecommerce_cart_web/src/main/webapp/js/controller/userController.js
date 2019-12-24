app.controller('userController' ,function($scope,userService){
    //获取用户名
    $scope.loginFlag=false;
    $scope.username='';
    //获取用户名
    $scope.getName=function(){
        console.log(111)
        userService.getName().success(
            function(response){
                $scope.loginFlag=response.success;
                if(response.success){
                    $scope.username=response.message;
                }
                console.log(response)
            }
        );
    }

    //搜索跳转
    $scope.search=function(){
        location.href="http://localhost:9104/ecommerce_search_web/#?keywords="+$scope.keywords;
    }
});
