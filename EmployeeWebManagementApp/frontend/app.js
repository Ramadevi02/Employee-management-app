var app = angular.module('myApp', []);

app.controller('MainController', function($scope, $http){
    $scope.employeeList = [];
    $http.get('http://localhost:8080/api/getAllEmployee')
    .then(function(response){
        $scope.employeeList = response.data;
        console.log($scope.employeeList);
    },function(error){
        console.error(error);
    });

    $scope.delEmployee = function(id){
        if(confirm('Are you sure you want to delete this employee')){
            $http.delete('http://localhost:8080/api/deleteEmployee/' + id)
                .then(function(response){
                    $scope.employeeList = response.data.data;
                    console.log(response.data.data);
                }, function(error){
                    console.error('error deleting employee : ' , error);
                }
            )
        }
    };

    $scope.startEdit = function(employee){
        $scope.editEmp = angular.copy(employee);
        $scope.editEmp.dob = new Date($scope.editEmp.dob);
    };

    $scope.updateEmployee = function(id){
        $http.put('http://localhost:8080/api/updateEmployee/' + id, $scope.editEmp, {
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function(response){
            alert('Employee updated Successfully');
            console.log(response.data);
            $scope.editEmp = null;
            $scope.employeeList = response.data.data;
        }, function(error){
            console.error('Error updating employee', error);
        })
    };
});