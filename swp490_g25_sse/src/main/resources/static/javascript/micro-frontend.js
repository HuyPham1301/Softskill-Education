/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


const renderMicroService = (serviceName, serviceContainerId, props) =>
    window[`render${serviceName}`] && window[`render${serviceName}`](serviceContainerId, undefined, props);
const downloadMicroService = (serviceName, props) => {
    const containerId = `microservice-${serviceName}`;
    const scriptId = `microservice-${serviceName}-script`;
    const cssId = `microservice-${serviceName}-css`;

    if (document.getElementById(scriptId) || document.getElementById(cssId)) {
        renderMicroService(serviceName, containerId, props);
    } else {
        fetch(`/asset-manifest.json`)
                .then(res => res.json())
                .then(manifest => {
                    const script = document.createElement('script');
                    script.id = scriptId;
                    script.src = `${manifest.files['main.js']}`;
                    script.onload = () => renderMicroService(serviceName, containerId, props);
                    document.head.appendChild(script);

                    if (manifest.files['main.css']) {
                        const link = document.createElement('link');
                        link.id = cssId;
                        link.rel = 'stylesheet';
                        link.type = 'text/css';
                        link.media = 'all';
                        link.href = `${manifest.files['main.css']}`;
                        document.head.appendChild(link);
                    }
                }).catch(err => console.error(err));
    }
}