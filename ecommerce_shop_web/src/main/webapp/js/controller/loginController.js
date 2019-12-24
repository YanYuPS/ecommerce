app.controller("loginController",function ($scope,$controller,loginService) {
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



})