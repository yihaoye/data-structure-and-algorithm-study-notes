// https://www.youtube.com/watch?v=rUbZcRgaSY4

// wrong example:
for (Integer id : employeeIds) {
    // Step 1: fetch employee details from db
    Future<Employee> future = service.submit(new EmployeeFetcher(id));
    Employee emp = future.get(); // blocking
    // Step 2: fetch employee tax rate from rest service
    Future<TaxRate> rateFuture = service.submit(new TaxRateFetcher(emp));
    TaxRate taxRate = rateFuture.get(); // blocking
    // Step 3: calculate current year tax
    BigDecimal tax = calculateTax(emp, taxRate);
    // Step 4: send email to employee using rest service
    service.submit(new SendEmail(emp, tax));
}

// correct example:
// callback chaining, similar to JavaScript
for (Integer id : employeeIds) {
    CompletableFuture.supplyAsync(() -> fetchEmployee(id))
        .thenApplyAsync(employee -> fetchTaxRate(employee))
        .thenApplyAsync(taxRate -> calculateTax(taxRate))
        .thenAcceptAsync(taxValue -> sendEmail(taxValue));
}



/* 
Java NIO 
    Buffer, Channels, Selectors
    Low-level API for asynchronous / non-blocking IO
    Applicable for Files, Sockets
    Listener based (callbacks)
*/
ByteBuffer buffer = new ByteBuffer.allocate(1024);

Path path = Paths.get("/home/file2");
AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOPtion.READ);

fileChannel.read(buffer, 0 , buffer, new CompletionHandler<Integer, ByteBuffer>() {
    @Override
    public void completed(Integer result, ByteBuffer data) { // callback method
        // process data
    }
});
