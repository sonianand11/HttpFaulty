This is a small utility which helps android developer to prevent application crash due to HttpReqest call. Just add the small class HttpFaulty in your java file and instead of create HttpDefault client object create HttpFaulty object and pass necessary parameter it wants, and its done!!. now just write the code what you wanted to do on crash or on Exception. Sample is give in the com/httpfaulty/example

<pre>
 Parameter Specification:
 
 <b>url: </b>http url where you want to make request
 <b>header : </b>it is hash of string, if you wish to add request header
 <b>handler: </b>which will excetue code in current activity when exception occurs
 <b>retry: </b>how many times you want to retry. if you want to make infinite retry then pass negative value. 
 <b>retryInt: </b>time of interval between each try. it is in milliseconds
 
 </pre> 