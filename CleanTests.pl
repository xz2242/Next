#!/usr/bin/perl -w

@testFileDirs = <Tests/*>;

foreach $testFileDir(@testFileDirs) {
    system("rm ". $testFileDir . "/Next" . '\$' . "Type.class");
    
    @testFileDir = <$testFileDir/*>;
    foreach $testFile(@testFileDir) {
        if(!($testFile =~ /(\.next)$/i) && !($testFile =~ /reference/i)) {
            system("rm " . $testFile);
        }
    }
}
