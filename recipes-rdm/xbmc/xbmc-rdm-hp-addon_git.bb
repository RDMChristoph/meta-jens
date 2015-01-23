DESCRIPTION = "XBMC HomePilot addon"

LICENSE = "GPLv2"
DEPENDS += "xbmc"
LICENSE = "GPLv3"
#LIC_FILES_CHKSUM = "file://script.homepilot/LICENSE.txt;md5=7226e01ff2d738e2246d467214c409b9"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

PR = "r0"
PV = "0.9.13.1"
SRC_URI = "git://github.com/rdm-dev/xbmc-rdm-hp-addon;rev=d216b776791cf88f4f6a8c6d136677388b1b07e1"

DEPENDS += "xbmc-startup"
RDEPENDS_${PN} += "xbmc-startup"

XBMC_USER_HOME = "/home/xbmc"
XBMC_APPDIR = "${XBMC_USER_HOME}/.xbmc"
XBMC_ADDONS = "${XBMC_APPDIR}/addons"

S = "${WORKDIR}/git/script.homepilot/"
do_install() {
        install -o xbmc -g users -d ${D}${XBMC_USER_HOME}
        install -o xbmc -g users -d ${D}${XBMC_APPDIR}
        install -o xbmc -g users -d ${D}${XBMC_ADDONS}
        cp -axr ${S} ${D}${XBMC_ADDONS}
	chown -R xbmc:users ${D}${XBMC_ADDONS}
}

FILES_${PN} += "${XBMC_USER_HOME}"