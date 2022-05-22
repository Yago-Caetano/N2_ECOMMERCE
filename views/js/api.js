
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
 async function executeGetWithAuthentication(params)
 {
    return new Promise(
        function(resolve,reject){
                let token=localStorage.getItem('accessToken');
                if (token==null)
                {
                    reject('Usuário não autenticado!')
                   
                }
                fetch(`http://localhost:8081/${params.route}`,   {
                            headers: {
                                'Authorization': 'Bearer '+token,
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
 async function executePostWithAuthentication(params)
 {
    return new Promise(
        function(resolve,reject){
                let token=localStorage.getItem('accessToken');
                if (token==null)
                {
                    reject('Usuário não autenticado!')
                   
                }
                fetch(`http://localhost:8081/${params.route}`,   {
                            method: 'POST',
                            headers: {
                               
                                'Authorization': 'Bearer '+token,
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(params.data)
                            
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

 async function executePutWithAuthentication(params)
 {
    return new Promise(
        function(resolve,reject){
                let token=localStorage.getItem('accessToken');
                if (token==null)
                {
                    reject('Usuário não autenticado!')
                   
                }
                fetch(`http://localhost:8081/${params.route}`,   {
                            method: 'PUT',
                            headers: {
                               
                                'Authorization': 'Bearer '+token,
                                'Accept': 'application/json',
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(params.data)
                            
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
 