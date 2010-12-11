#!/usr/bin/perl -w

use File::Compare;
use FileHandle;

system("perl CleanTests.pl");

my @failedTestFiles = ();

@testFileDirs = <Tests/*>;

foreach $testFileDir(@testFileDirs) {
    @testFiles = <$testFileDir/*>;
    foreach $testFile(@testFiles) {
        if($testFile =~ /\.next$/i) {
            #do the compilation
            if(system("./next < " . $testFile . " > " . $testFileDir . "/Next.java") == 0) {
                #do java compilation
                if(system("javac " . $testFileDir . "/Next.java") == 0) {
                    system("java -classpath " . $testFileDir . " Next > " . $testFileDir . "/output");
                }
            }
            
            #compare output with reference
            #if comparison returns 0 they are the same, otherwise they are not
            #write to log file of ones that are not
            if(File::Compare::compare_text($testFileDir . "/output", $testFileDir . "/reference")) {
                push(@failedTestFiles, $testFileDir);
            }
            
            print $testFile . "\n";

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