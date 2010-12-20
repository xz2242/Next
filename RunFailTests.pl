#!/usr/bin/perl -w

use File::Compare;
use FileHandle;

my @failedTestFiles = ();

@testFileDirs = <FailedTests/*>;

foreach $testFileDir(@testFileDirs) {
    @testFileDir = <$testFileDir/*>;
    foreach $testFile(@testFileDir) {
        if($testFile =~ /\.next$/i) {
            #do the compilation and record if the compilation was successful
            if(system("./next < " . $testFile . " > " . $testFileDir . "/Next.java") == 0) {
                push(@failedTestFiles, $testFileDir);
            }
        }
    }
}


#write to log file
$fh = FileHandle -> new();
if($fh -> open("> FailTests/SuccesfulTests.txt")) {
    foreach $file(@failedTestFiles) {
        $fh -> print($file . "\n");
    }
}