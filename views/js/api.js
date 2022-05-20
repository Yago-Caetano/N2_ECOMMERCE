
 async function executeGet(params)
 {
    return new Promise(
        function(resolve,reject){
            
                fetch(`http://localhost:8081/${params.route}`,   {
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            }
                            
                        }).then(resp=>{
                            if (!resp.ok) {
                                throw new Error(`HTTP error: ${resp.status}`);
                              }
       
                            const json = resp.json();
                            resolve(json)

                        }).catch(err=>{
                            reject(err)

                        })

  
            
            
            
        }
        
    ) 
    
 }
 