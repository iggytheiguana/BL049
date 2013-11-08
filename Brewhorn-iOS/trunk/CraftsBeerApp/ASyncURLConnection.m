#define kTokenService @"getToken"
#import "AsyncURLConnection.h"

static NSOperationQueue *sharedQueue = nil;

@implementation AsyncURLConnection

+ (id)sharedQueue {
     
        if (sharedQueue == nil)
            sharedQueue = [[NSOperationQueue alloc] init];
   
    return sharedQueue;
}

+ (id)request:(NSMutableURLRequest *)request completeBlock:(completeBlock_t)completeBlock errorBlock:(errorBlock_t)errorBlock
{
	return [[self alloc] initWithRequest:request
		completeBlock:completeBlock errorBlock:errorBlock];
}

- (id)initWithRequest:(NSMutableURLRequest *)request completeBlock:(completeBlock_t)completeBlock errorBlock:(errorBlock_t)errorBlock {
    
	if ((self = [super
			initWithRequest:request delegate:self startImmediately:NO])) {
		data_ = [[NSMutableData alloc] init];
		completeBlock_ = [completeBlock copy];
		errorBlock_ = [errorBlock copy];
        currentRequest_ = [request mutableCopy];
		[self start];
	}
	return self;
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
	[data_ setLength:0];
}
- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
	[data_ appendData:data];
}
- (void)connectionDidFinishLoading:(NSURLConnection *)connection {

    completeBlock_(data_);
}
- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
	errorBlock_(error);
}

@end