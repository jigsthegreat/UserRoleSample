class UrlMappings {

	static mappings = {
	"/api/users"(resources:"user")
        "/api/users/changeStatus/$id"(controller:"user", action:"changeStatus")
        // "/$controller/$action?/$id?(.$format)?"{
        //     constraints {
        //         // apply constraints here
        //     }
        // }

        "/"(uri:"/js/index.html")
        "500"(view:'/error')
	}
}
