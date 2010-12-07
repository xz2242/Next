#!/usr/bin/perl -w

use File::Compare;
use FileHandle;

my @failedTestFiles = ();

@testFileDirs = <Tests/*>;

#remove previously compiled files
foreach $testFileDir(@testFileDirs) {
    @testFileDir = <$testFileDir/*>;
    foreach $testFile(@testFileDir) {
        # remove any previously compiled files
        if($testFile =~ /\.java$/i) {
            system("rm " . $testFile);
        }
    }
}


foreach $testFileDir(@testFileDirs) {
    @testFileDir = <$testFileDir/*>;
    foreach $testFile(@testFileDir) {
        if($testFile =~ /\.next$/i) {
            #do the compilation
            system("./next < " . $testFile . " > " . $testFile . ".java");
            
            #compare output with reference
            #if comparison returns 0 they are the same, otherwise they are not
            #write to log file of ones that are not
            if(File::Compare::compare_text($testFile . ".java", $testFile . ".reference")) {
                push(@failedTestFiles, $testFile);
            }

        }
    }
}

#write to log file
$fh = FileHandle -> new();
if($fh -> open("> Tests/FailedTests.txt")) {
    foreach $file(@failedTestFiles) {
        $fh -> print($file . "\n");
    }
}