This is a small utility which helps android developer to prevent application crash due to HttpReqest call. Just add the small class HttpFaulty in your java file and instead of create HttpDefault client object create HttpFaulty object and pass necessary parameter it wants, and its done!!. now just write the code what you wanted to do on crash or on Exception. Sample is give in the com/httpfaulty/example

 Parameter Specification:
 
 url: http url where you want to make request
 header : it is hash of string, if you wish to add request header
 handler: which will excetue code in current activity when exception occurs
 retry: how many times you want to retry. if you want to make infinite retry then pass negative value. 
 retryInt: time of interval between each try. it is in milliseconds 