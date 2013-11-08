package com.craftbeer.httpcall;

public interface HttpListener {
void onResponse(String response);
void onError();

void onAlreadyExist(String response);
}
