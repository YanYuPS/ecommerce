//品牌控制层
app.controller('baseController' ,function($scope){

	//分页控件配置 
	$scope.paginationConf = {
		currentPage: 1,
		totalItems: 10,
		itemsPerPage: 10,
		perPageOptions: [10, 20, 30, 40, 50],
		onChange: function(){
			$scope.reloadList();//重新加载
		}
	};
	//重新加载列表 数据
	$scope.reloadList=function(){
		//重新加载时，清空选中
		$scope.selectIds=[];
		var nameAll = document.getElementsByName("checkboxAll");
		nameAll[0].checked = false;

		$scope.search( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
	}

	$scope.selectIds=[];//选中的ID集合
	//更新复选
	$scope.updateSelection = function($event, id) {
		if($event.target.checked){//如果是被选中,则增加到数组
			$scope.selectIds.push( id);
		}else{
			var idx = $scope.selectIds.indexOf(id);
			$scope.selectIds.splice(idx, 1);//删除
		}

		var name = document.getElementsByName("checkboxItem");
		var nameAll = document.getElementsByName("checkboxAll");
		if($scope.selectIds.length==name.length){//全选
			nameAll[0].checked = true;
		}else{//取消全选
			nameAll[0].checked = false;
		}
	}
	//更新复选
	$scope.updateSelectionAll = function($event, list, key) {
		$scope.selectIds=[];
		var name = document.getElementsByName("checkboxItem");

		if($event.target.checked){//如果是被选中,则增加到数组
			for (var i = 0; i < name.length; i++) {
				name[i].checked = true;
			}

			for (var i = 0; i < list.length; i++) {
				// console.log(list[i])
				$scope.selectIds.push(list[i][key]);
			}
		}else{
			for (var i = 0; i < name.length; i++) {
				name[i].checked = false;
			}
		}
	}

	/**
	 * 将json中的某个属性值提取出来，用逗号 拼接成新的字符串
	 * @param jsonString
	 * @param key 需要提取的属性
	 * @returns {string}
	 */
	$scope.jsonToString=function(jsonString,key){
		var json= JSON.parse(jsonString);
		var value="";
		for(var i=0;i<json.length;i++){
			if(i>0){
				value+=",";
			}
			value += json[i][key];
		}
		return value;
	}

});	