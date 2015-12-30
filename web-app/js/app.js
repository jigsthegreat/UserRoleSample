var app = angular.module('User', ['ngResource', 'ui.bootstrap', 'ui.router', 'satellizer', 'ui.router.title']);

app.factory('User', function ($resource) {
    return {
        User: $resource('../UserRoleSample/api/users/:user', {}),
        ChangeStatus: $resource('../UserRoleSample/api/users/changeStatus/:id')
    }
});

app.controller('authController', function ($scope, $auth, $state, $window) {
    $scope.login = function () {
        var credentials = {
            username: $scope.auth.username,
            password: $scope.auth.password
        }

        $auth.login(credentials).then(function (data) {
            $state.go('home', {});
            setTimeout(function () {
                $window.location.reload();
            }, 10);
        }).catch(function () {
            $scope.error = true;
        });
    };
});

app.controller('userController', function ($scope, $auth, $state, $window, User) {
    $scope.isAuthenticated = function () {
        return $auth.isAuthenticated();
    };
    $scope.logout = function () {
        $auth.logout();
        $state.go('login', {});
    };
    $scope.hello = "JIGS";

    $scope.changeStatus = function(user) {
        User.ChangeStatus.get({id: user});
        console.log("Happy");
    }

    $scope.users = User.User.query();
});

// app.run(function ($rootScope, $auth) {
//     $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
//         var requireLogin = toState.data.requireLogin;

//         if (requireLogin && !$auth.isAuthenticated()) {
//             event.preventDefault();
//         } else if (!requireLogin && $auth.isAuthenticated()) {
//             event.preventDefault();
//         }
//     });
// });

app.config(function ($stateProvider, $urlRouterProvider, $locationProvider, $authProvider) {
    $authProvider.tokenName = 'access_token';
    $authProvider.loginUrl = '/UserRoleSample/api/login';
    // $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise("/login")

    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: "js/login.html",
            controller: 'authController',
            // data: {
            //     requireLogin: false
            // },
            resolve: {
                $title: function () {
                    return 'Login';
                }
            }
        })
        // .state('register', {
        //     url: "/register",
        //     templateUrl: "register.html",
        //     controller: 'userController',
        //     data: {
        //         requireLogin: false
        //     },
        //     resolve: {
        //         $title: function () {
        //             return 'Register';
        //         }
        //     }
        // })
        .state('home', {
            url: "/home",
            templateUrl: "js/home.html",
            controller: 'userController',
            // data: {
            //     requireLogin: true
            // },
            resolve: {
                $title: function () {
                    return 'Home';
                }
            }
        });
});