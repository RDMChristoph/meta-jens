DESCRIPTION = "This software will provide the HomePilot2 Service Monitor e.g :81 Interface"
MAINTAINER= "HP2 Dev Team <verteiler.hp2dev.team@rademacher.de>"
HOMEPAGE = "http://www.rademacher.de"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${THISDIR}/files/license.txt;md5=3ebe3464e841ddbf115af1f7019017c5"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

RDEPENDS_${PN} += "daemontools-encore"
RDEPENDS_${PN} += "system-image-update"

RDEPENDS_${PN} += "dancer2-perl"
RDEPENDS_${PN} += "encode-perl"
RDEPENDS_${PN} += "file-find-rule-perl"
RDEPENDS_${PN} += "file-slurp-tiny-perl"
RDEPENDS_${PN} += "http-tiny-perl"
RDEPENDS_${PN} += "json-perl"
RDEPENDS_${PN} += "module-pluggable-perl"
RDEPENDS_${PN} += "moo-perl"
RDEPENDS_${PN} += "namespace-clean-perl"
RDEPENDS_${PN} += "net-async-http-server-perl"
RDEPENDS_${PN} += "perl-modules"
RDEPENDS_${PN} += "plack-perl"
RDEPENDS_${PN} += "scalar-list-utils-perl"
RDEPENDS_${PN} += "unix-statgrab-perl"
RDEPENDS_${PN} += "yaml-libyaml-perl"
#RDEPENDS_${PN} += "dancer2-plugin-auth-yarbac-perl"

RDEPENDS_${PN}-dev += "devel-cycle-perl"
RDEPENDS_${PN}-dev += "devel-leak-object-perl"
RDEPENDS_${PN}-dev += "devel-stacktrace-perl"
RDEPENDS_${PN}-dev += "test-leaktrace-perl"
RDEPENDS_${PN}-dev += "test-memory-cycle-perl"

HP2SM_REV="5147"
PV = "0.1-${HP2SM_REV}"

SRC_URI += "svn://192.168.1.186/svn/EW_Prj/001/HP_ServiceMonitor/branches/next;protocol=http;module=hp2sm;rev=${HP2SM_REV}"
SRC_URI += "file://hp2sm.run"
SRC_URI += "file://hp2sm-log.run"

HP2SM_BASE="/opt/rdm/hp2sm"
SERVICE_ROOT = "${sysconfdir}/daemontools/service"
HP2SM_SERVICE_DIR = "${SERVICE_ROOT}/hp2sm"

do_install() {
	# create service directory
	install -d ${D}${HP2SM_SERVICE_DIR}
	install -d ${D}${HP2SM_SERVICE_DIR}/log
	
	# install svc run script and make it executable
	install -m 0755 ${WORKDIR}/hp2sm.run ${D}${HP2SM_SERVICE_DIR}/run
	install -m 0755 ${WORKDIR}/hp2sm-log.run ${D}${HP2SM_SERVICE_DIR}/log/run
	
	# create directory for source
	install -d ${D}${HP2SM_BASE}

	# copy source
	(cd ${WORKDIR}/hp2sm/src && tar cf - .) | (cd ${D}${HP2SM_BASE} && tar xf -)
    chown -R root:root ${D}${HP2SM_BASE}
}

FILES_${PN} += "${HP2SM_BASE}"
FILES_${PN} += "${SERVICE_ROOT}"

PACKAGES =+ "${PN}-ethtool"
PACKAGES =+ "${PN}-xbmc"
PACKAGES =+ "${PN}-zway"

RDEPENDS_${PN}-zway += "zway-stick-updater"
RDEPENDS_${PN}-ethtool += "init-ifupdown"
RDEPENDS_${PN}-xbmc_append_curie += "init-iecset"

FILES_${PN}-dev += "${HP2SM_BASE}/t"
FILES_${PN}-ethtool += "${HP2SM_BASE}/lib/hp2sm/Plugins/Ethtool.pm"
FILES_${PN}-ethtool += "${HP2SM_BASE}/views/ethtool.tt"
FILES_${PN}-xbmc += "${HP2SM_BASE}/lib/hp2sm/Plugins/Mediaplayer.pm"
FILES_${PN}-xbmc += "${HP2SM_BASE}/lib/hp2sm/RestAPI/System/Services/Plugins/xbmc.pm"
FILES_${PN}-xbmc += "${HP2SM_BASE}/views/mediaplayer.tt"
FILES_${PN}-xbmc += "${HP2SM_BASE}/views/navigation/40-mediaplayer.tt"
FILES_${PN}-zway += "${HP2SM_BASE}/lib/hp2sm/Plugins/ZWave.pm"
FILES_${PN}-zway += "${HP2SM_BASE}/lib/hp2sm/RestAPI/System/Services/Plugins/z_way.pm"
FILES_${PN}-zway += "${HP2SM_BASE}/views/navigation/60-zwave.tt"
FILES_${PN}-zway += "${HP2SM_BASE}/views/zwave.tt"
