try {
	$version = "${version}"
	$appName = "${artifactId}"
	$scriptPath = (Split-Path -parent $MyInvocation.MyCommand.Definition)
	$warPath = "$scriptPath\$appName-$version.war"
	Write-Debug "WarPath: $warPath"
	

    Write-ChocolateySuccess 'War Deployment Examples Installed!!!!!!!!!!'
} catch {
	Write-ChocolateyFailure $appName $($_.Exception.Message)
	throw
}