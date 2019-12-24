app.controller('userController' ,function($scope,$controller   ,userService){
    //注册
    $scope.reg=function(){
        if($scope.entity.password!=$scope.password)  {
            alert("两次输入的密码不一致，请重新输入");
            return ;
        }

        console.log($scope.entity)

        userService.add($scope.entity).success(
            function(response){
                if(response.success){
                    //注册成功, 去登录
                    location.href="http://localhost:9106/ecommerce_user_web";
                }else{
                    alert(response.message);
                }
            }
        );
    }
});
