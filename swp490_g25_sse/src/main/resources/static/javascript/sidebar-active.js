
let sidebarLocation = document.getElementById("sidebarLocation").value;
console.log(sidebarLocation);


switch(sidebarLocation){
    case "listCourse":
    document.getElementById("listCourse").classList.add("active");
    break;

    case "listRegistedCourse":
    document.getElementById("listRegistedCourse").classList.add("active");
    break;

    case "course":
    document.getElementById("course").classList.add("active");
    break;

    case "overview":
    document.getElementById("overview").classList.add("active");
    break;

    case "milestone":
    document.getElementById("milestone").classList.add("active");
    break;
    
    case "forum":
    document.getElementById("forum").classList.add("active");
    break;

    case "createCourse":
    document.getElementById("createCourse").classList.add("active");
    break;
}
