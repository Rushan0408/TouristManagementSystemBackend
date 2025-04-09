<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tourist Information System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        h1 {
            color: #336699;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .endpoints {
            margin-top: 20px;
        }
        .endpoint {
            margin-bottom: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-left: 3px solid #336699;
        }
        .method {
            font-weight: bold;
            color: #336699;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Tourist Information System</h1>
        <p>Welcome to the Tourist Information System API. Below are the available endpoints:</p>
        
        <div class="endpoints">
            <h2>Tourist Attractions</h2>
            
            <div class="endpoint">
                <span class="method">GET</span> /attractions
                <p>Get all tourist attractions</p>
            </div>
            
            <div class="endpoint">
                <span class="method">GET</span> /attractions/{id}
                <p>Get a tourist attraction by ID</p>
            </div>
            
            <div class="endpoint">
                <span class="method">GET</span> /attractions/{category}
                <p>Get tourist attractions by category</p>
            </div>
            
            <div class="endpoint">
                <span class="method">POST</span> /attractions
                <p>Create a new tourist attraction</p>
            </div>
            
            <div class="endpoint">
                <span class="method">PUT</span> /attractions/{id}
                <p>Update a tourist attraction</p>
            </div>
            
            <div class="endpoint">
                <span class="method">DELETE</span> /attractions/{id}
                <p>Delete a tourist attraction</p>
            </div>
            
            <h2>Accommodations</h2>
            
            <div class="endpoint">
                <span class="method">GET</span> /accommodations
                <p>Get all accommodations</p>
            </div>
            
            <div class="endpoint">
                <span class="method">GET</span> /accommodations/{id}
                <p>Get an accommodation by ID</p>
            </div>
            
            <div class="endpoint">
                <span class="method">GET</span> /accommodations/{type}
                <p>Get accommodations by type</p>
            </div>
            
            <div class="endpoint">
                <span class="method">POST</span> /accommodations
                <p>Create a new accommodation</p>
            </div>
            
            <h2>Transportation</h2>
            
            <div class="endpoint">
                <span class="method">GET</span> /transportation
                <p>Get all transportation options</p>
            </div>
            
            <div class="endpoint">
                <span class="method">GET</span> /transportation/{id}
                <p>Get a transportation option by ID</p>
            </div>
            
            <div class="endpoint">
                <span class="method">GET</span> /transportation/{type}
                <p>Get transportation options by type</p>
            </div>
            
            <div class="endpoint">
                <span class="method">POST</span> /transportation
                <p>Create a new transportation option</p>
            </div>
            
            <h2>Search</h2>
            
            <div class="endpoint">
                <span class="method">GET</span> /search?keyword={keyword}&category={category}
                <p>Search for tourist information. Category can be attractions, accommodations, transportation, or all.</p>
            </div>
        </div>
    </div>
</body>
</html>
*/